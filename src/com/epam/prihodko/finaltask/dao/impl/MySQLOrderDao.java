package com.epam.prihodko.finaltask.dao.impl;

import com.epam.prihodko.finaltask.controller.listener.ContextServletListener;
import com.epam.prihodko.finaltask.dao.DataBaseParameterName;
import com.epam.prihodko.finaltask.dao.entity.OrderDao;
import com.epam.prihodko.finaltask.entity.Order;
import com.epam.prihodko.finaltask.exception.daoException.ConnectionPoolException;
import com.epam.prihodko.finaltask.exception.daoException.DaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.*;

public class MySQLOrderDao implements OrderDao{
    private final static String getById = "select * from mydb.order where id=?";
    private final static String createOrder = "insert into mydb.order (apartment_class,room_number," +
            "couchette,date_in,date_out,person_id,status) values(?,?,?,?,?,?,?)";
    private final static String updateAppartment = "update mydb.order set couchette=?, date_in=?, " +
            "date_out=?, status=?, apartment_class=?, room_number=? where id=?";
    private final static String deleteById = "delete from mydb.order where id=?";
    private final static String getAllOrders = "select * from mydb.order";
    private final static String getOrderMapByPersonId = "select * from mydb.order where person_id=?";
    private final static String getOrderMapByStatus = "select * from mydb.order where status=?";

    public Order getById(int domainId) throws DaoException {
        Order order = null;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet =null;
        try{
            connection =  ContextServletListener.connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(getById);
            preparedStatement.setInt(1, domainId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                order = new Order();
                order.setId(resultSet.getInt(DataBaseParameterName.ID));
                order.setApartmentClass(resultSet.getString(DataBaseParameterName.APARTMENT_CLASS));
                order.setRoomNumber(resultSet.getInt(DataBaseParameterName.ROOM_NUMBER));
                order.setCouchette(resultSet.getInt(DataBaseParameterName.COUCHETTE));

                order.setDate_in(resultSet.getDate(DataBaseParameterName.DATE_IN));
                order.setDate_out(resultSet.getDate(DataBaseParameterName.DATE_OUT));
                order.setStatus(resultSet.getNString(DataBaseParameterName.STATUS));
            }
        }catch (SQLException e){
            throw new DaoException("MySQLOrderDao has problem with Sql in getById method",e);
        }catch (ConnectionPoolException e) {
            throw new DaoException("MySQLOrderDao has problem with connection pool in getById method",e);
        }
        finally {
            ContextServletListener.connectionPool.closeConnection(connection,preparedStatement,resultSet);

        }
        return order;
    }
    public void create(Order order)throws DaoException{
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try{
            connection =  ContextServletListener.connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(createOrder);
            preparedStatement.setString(1,order.getApartmentClass());
            preparedStatement.setInt(2, order.getRoomNumber());
            preparedStatement.setInt(3, order.getCouchette());
            preparedStatement.setDate(4, order.getDate_in());
            preparedStatement.setDate(5, order.getDate_out());
            preparedStatement.setInt(6,order.getPersonId());
            preparedStatement.setString(7,order.getStatus());
            preparedStatement.execute();
        }catch (SQLException e){
            throw new DaoException("MySQLOrderDao has problem with Sql in create method",e);
        }catch (ConnectionPoolException e) {
            throw new DaoException("MySQLOrderDao has problem with connection pool in create method",e);
        } finally {
            ContextServletListener.connectionPool.closeConnection(connection, preparedStatement);
        }
    }
    public void update (Order order) throws DaoException{
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try{
            connection =  ContextServletListener.connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(updateAppartment);
            preparedStatement.setInt(1, order.getCouchette());
            preparedStatement.setDate(2,order.getDate_in());
            preparedStatement.setDate(3, order.getDate_out());
            preparedStatement.setString(4, order.getStatus());
            preparedStatement.setString(5, order.getApartmentClass());
            preparedStatement.setInt(6, order.getRoomNumber());
            preparedStatement.setInt(7, order.getId());
            preparedStatement.execute();

        }catch (SQLException e){
            throw new DaoException("MySQLOrderDao has problem with Sql in update method",e);
        }catch (ConnectionPoolException e) {
            throw new DaoException("MySQLOrderDao has problem with connection pool in update method",e);
        }
        finally {
            ContextServletListener.connectionPool.closeConnection(connection, preparedStatement);
        }

    }
    public void delete (Order order) throws DaoException{
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try{
            connection =  ContextServletListener.connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(deleteById);
            preparedStatement.setInt(1,order.getId());
            preparedStatement.execute();

        }catch (SQLException e){
            throw new DaoException("MySQLOrderDao has problem with Sql in delete method",e);
        }catch (ConnectionPoolException e) {
            throw new DaoException("MySQLOrderDao has problem with connection pool in delete method",e);
        }
        finally {
            ContextServletListener.connectionPool.closeConnection(connection, preparedStatement);
        }
}
    public Map<Integer, Order> getOrderMapByPersonId(int personId)throws DaoException{
        Map<Integer, Order> mapOrder = new HashMap<Integer, Order>();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet =null;
        try{
            connection =  ContextServletListener.connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(getOrderMapByPersonId);
            preparedStatement.setInt(1, personId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt(DataBaseParameterName.ID);
                String apartment_class = resultSet.getString(DataBaseParameterName.APARTMENT_CLASS);
                int room_number = resultSet.getInt(DataBaseParameterName.ROOM_NUMBER);
                int couchette = resultSet.getInt(DataBaseParameterName.COUCHETTE);
                Date date_in = resultSet.getDate(DataBaseParameterName.DATE_IN);
                Date date_out = resultSet.getDate(DataBaseParameterName.DATE_OUT);
                String status = resultSet.getString(DataBaseParameterName.STATUS);
                Order order = new Order(id,apartment_class,room_number,couchette,date_in,date_out,status);
                mapOrder.put(id,order);
            }

        }catch (SQLException e){
            throw new DaoException("MySQLOrderDao has problem with Sql in getOrderMapByPersonId method",e);
        }catch (ConnectionPoolException e) {
            throw new DaoException("MySQLOrderDao has problem with connection pool in getOrderMapByPersonId method",e);
        } finally {
            ContextServletListener.connectionPool.closeConnection(connection, preparedStatement,resultSet);
        }
        return mapOrder;
    }
    public Map<Integer, Order> getOrderMapByStatus(String status)throws DaoException{
        Map<Integer, Order> mapOrder = new HashMap<Integer, Order>();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet =null;
        try{
            connection =  ContextServletListener.connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(getOrderMapByStatus);
            preparedStatement.setString(1,status);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt(DataBaseParameterName.ID);
                String apartment_class = resultSet.getString(DataBaseParameterName.APARTMENT_CLASS);
                int room_number = resultSet.getInt(DataBaseParameterName.ROOM_NUMBER);
                int couchette = resultSet.getInt(DataBaseParameterName.COUCHETTE);
                Date date_in = resultSet.getDate(DataBaseParameterName.DATE_IN);
                Date date_out = resultSet.getDate(DataBaseParameterName.DATE_OUT);
                String statusOr = resultSet.getString(DataBaseParameterName.STATUS);
                Order order = new Order(id,apartment_class,room_number,couchette,date_in,date_out,statusOr);
                mapOrder.put(id, order);
            }

        }catch (SQLException e){
            throw new DaoException("MySQLOrderDao has problem with Sql in getOrderMapByStatus method",e);
        }catch (ConnectionPoolException e) {
            throw new DaoException("MySQLOrderDao has problem with connection pool in getOrderMapByStatus method",e);
        } finally {
            ContextServletListener.connectionPool.closeConnection(connection, preparedStatement,resultSet);
        }
        return mapOrder;
    }
    public Map<Integer,Order> getAllOrdersMap()throws DaoException{
        Map<Integer, Order> mapOrder = new HashMap<Integer, Order>();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet =null;
        try{
            connection =  ContextServletListener.connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(getAllOrders);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt(DataBaseParameterName.ID);
                String apartment_class = resultSet.getString(DataBaseParameterName.APARTMENT_CLASS);
                int room_number = resultSet.getInt(DataBaseParameterName.ROOM_NUMBER);
                int couchette = resultSet.getInt(DataBaseParameterName.COUCHETTE);
                Date date_in = resultSet.getDate(DataBaseParameterName.DATE_IN);
                Date date_out = resultSet.getDate(DataBaseParameterName.DATE_OUT);
                String statusOr = resultSet.getString(DataBaseParameterName.STATUS);
                Order order = new Order(id,apartment_class,room_number,couchette,date_in,date_out,statusOr);
                mapOrder.put(id, order);
            }

        }catch (SQLException e){
            throw new DaoException("MySQLOrderDao has problem with Sql in getOrderMapByStatus method",e);
        }catch (ConnectionPoolException e) {
            throw new DaoException("MySQLOrderDao has problem with connection pool in getOrderMapByStatus method",e);
        } finally {
            ContextServletListener.connectionPool.closeConnection(connection, preparedStatement,resultSet);
        }
        return mapOrder;
    }

}
