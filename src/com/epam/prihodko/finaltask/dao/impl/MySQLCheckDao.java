package com.epam.prihodko.finaltask.dao.impl;

import com.epam.prihodko.finaltask.controller.listener.ContextServletListener;
import com.epam.prihodko.finaltask.dao.DataBaseParameterName;
import com.epam.prihodko.finaltask.dao.entity.CheckDao;
import com.epam.prihodko.finaltask.entity.Check;
import com.epam.prihodko.finaltask.exception.daoException.ConnectionPoolException;
import com.epam.prihodko.finaltask.exception.daoException.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MySQLCheckDao implements CheckDao{
    private final static String getCheckById = "select * from mydb.check where id=?";
    private final static String createCheck = "insert into mydb.check (price,apartment_id,order_id) values(?,?,?)";
    private final static String getCheckByOrderId = "select * from mydb.check where check.order_id=?";
    private final static String getCheckByApartmentId = "select * from mydb.check where check.apartment_id=?";
    private final static String deleteByOrderId = "delete from mydb.check where order_id=?";

    public Check getById(int domainId) throws DaoException{
        Check check = null;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet =null;
        try{
            connection =  ContextServletListener.connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(getCheckById);
            preparedStatement.setInt(1,domainId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                check = new Check();
                check.setId(resultSet.getInt(DataBaseParameterName.ID));
                check.setPrice(resultSet.getInt(DataBaseParameterName.PRICE));
                check.setApatrmentId(resultSet.getInt(DataBaseParameterName.APARTMENT_ID));
                check.setOrderId(resultSet.getInt(DataBaseParameterName.ORDER_ID));
            }

        }catch (SQLException e){
            throw new DaoException(" MySQLCheckDao has problem with Sql in getById method",e);
        }catch (ConnectionPoolException e) {
            throw new DaoException(" MySQLCheckDao has problem with connection pool in getById method",e);
        }
        finally {
            ContextServletListener.connectionPool.closeConnection(connection,preparedStatement,resultSet);
        }
        return check;
    }
    public void create(Check check)throws DaoException{
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try{
            connection =  ContextServletListener.connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(createCheck);
            preparedStatement.setInt(1, check.getPrice());
            preparedStatement.setInt(2, check.getApatrmentId());
            preparedStatement.setInt(3, check.getOrderId());
            preparedStatement.execute();

        }catch (SQLException e){
            throw new DaoException("MySQLCheckDao has problem with Sql in create method",e);
        }catch (ConnectionPoolException e) {
            throw new DaoException("MySQLCheckDao has problem with connection pool in create method",e);
        }
        finally {
            ContextServletListener.connectionPool.closeConnection(connection, preparedStatement);
        }
    }
    public void update(Check check) throws DaoException{}
    public void delete(Check check)throws DaoException{}
    public void deleteByOrderId(int orderId)throws DaoException{
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try{
            connection =  ContextServletListener.connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(deleteByOrderId);
            preparedStatement.setInt(1, orderId);
            preparedStatement.execute();

        }catch (SQLException e){
            throw new DaoException("MySQLCheckDao has problem with Sql in create method",e);
        }catch (ConnectionPoolException e) {
            throw new DaoException("MySQLCheckDao has problem with connection pool in create method",e);
        }
        finally {
            ContextServletListener.connectionPool.closeConnection(connection, preparedStatement);
        }
    }
    public Check getCheckByOrderId(int orderId)throws DaoException{
        Check check = null;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet =null;
        try{
            connection =  ContextServletListener.connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(getCheckByOrderId);
            preparedStatement.setInt(1,orderId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                check = new Check();
                check.setId(resultSet.getInt(DataBaseParameterName.ID));
                check.setPrice(resultSet.getInt(DataBaseParameterName.PRICE));
                check.setApatrmentId(resultSet.getInt(DataBaseParameterName.APARTMENT_ID));
                check.setOrderId(resultSet.getInt(DataBaseParameterName.ORDER_ID));
            }

        }catch (SQLException e){
            throw new DaoException("MySQLCheckDao has problem with Sql in getCheckByOrderId method",e);
        }catch (ConnectionPoolException e) {
            throw new DaoException("MySQLCheckDao has problem with connection pool in getCheckByOrderId method",e);
        }
        finally {
            ContextServletListener.connectionPool.closeConnection(connection,preparedStatement,resultSet);
        }
        return check;
    }
    public Map<Integer, Check> getCheckMapByApartmentId(int apartmentId)throws DaoException{
        Map<Integer, Check> mapCheck = new HashMap<Integer, Check>();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet =null;
        try{
            connection =  ContextServletListener.connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(getCheckByApartmentId);
            preparedStatement.setInt(1,apartmentId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Check check = new Check();
                check.setId(resultSet.getInt(DataBaseParameterName.ID));
                check.setPrice(resultSet.getInt(DataBaseParameterName.PRICE));
                check.setApatrmentId(resultSet.getInt(DataBaseParameterName.APARTMENT_ID));
                check.setOrderId(resultSet.getInt(DataBaseParameterName.ORDER_ID));
                mapCheck.put(check.getId(),check);
            }

        }catch (SQLException e){
            throw new DaoException("MySQLCheckDao has problem with Sql in getCheckByOrderId method",e);
        }catch (ConnectionPoolException e) {
            throw new DaoException("MySQLCheckDao has problem with connection pool in getCheckByOrderId method",e);
        }
        finally {
            ContextServletListener.connectionPool.closeConnection(connection,preparedStatement,resultSet);
        }
        return mapCheck;
    }
}
