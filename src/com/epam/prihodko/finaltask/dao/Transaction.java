package com.epam.prihodko.finaltask.dao;

import com.epam.prihodko.finaltask.controller.RequestParameterName;
import com.epam.prihodko.finaltask.controller.listener.ContextServletListener;
import com.epam.prihodko.finaltask.entity.*;
import com.epam.prihodko.finaltask.exception.daoException.ConnectionPoolException;
import com.epam.prihodko.finaltask.exception.daoException.DaoException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Transaction {

    private final static String getCheckByApartmentId = "select * from mydb.check where check.apartment_id=?";
    private final static String getCheckByOrderId = "select * from mydb.check where check.order_id=?";
    private final static String deleteByOrderId = "delete from mydb.check where order_id=?";
    private final static String deleteById = "delete from mydb.order where id=?";
    private final static String getAppartmentById = "select * from apartment where id=?";
    private final static String updateAppartment = "update mydb.apartment set couchette=?, " +
            "status=?, class_id=?, room_number=?, price=? where id=?";
/**
 * Performs transaction when you remove Order with 'ordered' atatus
 * */
    public void deleteOrderWithStatusOrdered(int orderId) throws DaoException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection =  ContextServletListener.connectionPool.takeConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            preparedStatement = connection.prepareStatement(getCheckByOrderId);
            preparedStatement.setInt(1,orderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Check check = new Check();
            while (resultSet.next()){
                check.setId(resultSet.getInt(DataBaseParameterName.ID));
                check.setPrice(resultSet.getInt(DataBaseParameterName.PRICE));
                check.setApatrmentId(resultSet.getInt(DataBaseParameterName.APARTMENT_ID));
                check.setOrderId(resultSet.getInt(DataBaseParameterName.ORDER_ID));
            }
            int apartmentId =0;
            if(check.getApatrmentId()!=0){
                apartmentId = check.getApatrmentId();
            }
            preparedStatement = connection.prepareStatement(deleteByOrderId);
            preparedStatement.setInt(1, orderId);
            preparedStatement.execute();
            preparedStatement = connection.prepareStatement(deleteById);
            preparedStatement.setInt(1,orderId);
            preparedStatement.execute();
            int flag = 0;
            Map<Integer,Check> checkMap = new HashMap<Integer, Check>();
            preparedStatement = connection.prepareStatement(getCheckByApartmentId);
            preparedStatement.setInt(1,apartmentId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Check check1 = new Check();
                check1.setId(resultSet.getInt(DataBaseParameterName.ID));
                check1.setPrice(resultSet.getInt(DataBaseParameterName.PRICE));
                check1.setApatrmentId(resultSet.getInt(DataBaseParameterName.APARTMENT_ID));
                check1.setOrderId(resultSet.getInt(DataBaseParameterName.ORDER_ID));
                checkMap.put(check.getId(),check);
            }
            for (Map.Entry<Integer, Check> ch : checkMap.entrySet()) {
                Check element = ch.getValue();
                if(element.getApatrmentId()==apartmentId){
                    flag++;
                }
            }
            if(flag==0){
                Apartment apartment = new Apartment();
                preparedStatement = connection.prepareStatement(getAppartmentById);
                preparedStatement.setInt(1,apartmentId);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    apartment.setId(resultSet.getInt(DataBaseParameterName.ID));
                    apartment.setPrice(resultSet.getInt(DataBaseParameterName.PRICE));
                    apartment.setCouchette(resultSet.getInt(DataBaseParameterName.COUCHETTE));
                    apartment.setRoomNumber(resultSet.getInt(DataBaseParameterName.ROOM_NUMBER));
                    apartment.setStatus(resultSet.getNString(DataBaseParameterName.STATUS));
                    apartment.setClassId(resultSet.getInt(DataBaseParameterName.CLASS_ID));
                }
                apartment.setStatus(RequestParameterName.APARTMENT_STATUS_AVAILABLE);
                preparedStatement = connection.prepareStatement(updateAppartment);
                preparedStatement.setInt(1,apartment.getCouchette());
                preparedStatement.setString(2, apartment.getStatus());
                preparedStatement.setInt(3, apartment.getClassId());
                preparedStatement.setInt(4, apartment.getRoomNumber());
                preparedStatement.setInt(5, apartment.getPrice());
                preparedStatement.setInt(6, apartment.getId());

                preparedStatement.execute();
            }
            connection.commit();
        }catch (SQLException e){
            throw new DaoException("Transaction has problem with Sql in findAvailableApartment method",e);
        }catch (ConnectionPoolException e) {
            throw new DaoException("Transaction has problem with connection pool in findAvailableApartment method",e);
        }
        finally {
            ContextServletListener.connectionPool.closeConnection(connection,preparedStatement);
        }
    }
}
