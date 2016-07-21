package com.epam.prihodko.finaltask.dao.impl;

import com.epam.prihodko.finaltask.controller.listener.ContextServletListener;
import com.epam.prihodko.finaltask.dao.DataBaseParameterName;
import com.epam.prihodko.finaltask.dao.entity.ApartmentDao;
import com.epam.prihodko.finaltask.entity.Apartment;
import com.epam.prihodko.finaltask.exception.daoException.ConnectionPoolException;
import com.epam.prihodko.finaltask.exception.daoException.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class MySQLApartmentDao implements ApartmentDao{
    private final static String getAppartmentById = "select * from apartment where id=?";
    private final static String updateAppartment = "update mydb.apartment set couchette=?, " +
            "status=?, class_id=?, room_number=?, price=? where id=?";
    private final static String createApartment = "insert into apartment (price,couchette,room_number,status,class_id) values(?,?,?,?,?)";
    private final static String getAllApartments = "select * from apartment";
    private final static String deleteById = "delete from mydb.apartment where id=?";
    private final static String findApartment = "select * from apartment where price=? and couchette=? and room_number=? and class_id=?";
    private final static String getApByCouchClassidRoom = "select * from apartment where class_id=? and couchette=? and room_number=?";

    public Apartment getById(int id)throws DaoException{
        Apartment apartment=null;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet =null;
        try{
            connection =  ContextServletListener.connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(getAppartmentById);
            preparedStatement.setInt(1,id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                apartment = new Apartment();
                apartment.setId(resultSet.getInt(DataBaseParameterName.ID));
                apartment.setPrice(resultSet.getInt(DataBaseParameterName.PRICE));
                apartment.setCouchette(resultSet.getInt(DataBaseParameterName.COUCHETTE));
                apartment.setRoomNumber(resultSet.getInt(DataBaseParameterName.ROOM_NUMBER));
                apartment.setStatus(resultSet.getNString(DataBaseParameterName.STATUS));
                apartment.setClassId(resultSet.getInt(DataBaseParameterName.CLASS_ID));
            }

        }catch (SQLException e){
            throw new DaoException("MySQLApartmentDao has problem with Sql in getById mathod",e);
        }catch (ConnectionPoolException e) {
            throw new DaoException("MySQLApartmentDao has problem with connection pool in getById mathod",e);
        }
        finally {
            ContextServletListener.connectionPool.closeConnection(connection, preparedStatement,resultSet);
        }

        return apartment;
    }
    public void create (Apartment apartment)throws DaoException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try{
            connection =  ContextServletListener.connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(createApartment);
            preparedStatement.setInt(1, apartment.getPrice());
            preparedStatement.setInt(2, apartment.getCouchette());
            preparedStatement.setInt(3, apartment.getRoomNumber());
            preparedStatement.setString(4, apartment.getStatus());
            preparedStatement.setInt(5, apartment.getClassId());
            preparedStatement.execute();

        }catch (SQLException e){
            throw new DaoException("MySQLApartmentDao has problem with Sql in update method",e);
        }catch (ConnectionPoolException e) {
            throw new DaoException("MySQLApartmentDao has problem with connection pool in update method",e);
        }
        finally {
            ContextServletListener.connectionPool.closeConnection(connection, preparedStatement);
        }
    }
    public void update (Apartment apartment)throws DaoException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try{
            connection =  ContextServletListener.connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(updateAppartment);

            preparedStatement.setInt(1,apartment.getCouchette());
            preparedStatement.setString(2, apartment.getStatus());
            preparedStatement.setInt(3, apartment.getClassId());
            preparedStatement.setInt(4, apartment.getRoomNumber());
            preparedStatement.setInt(5, apartment.getPrice());
            preparedStatement.setInt(6, apartment.getId());

            preparedStatement.execute();

        }catch (SQLException e){
            throw new DaoException("MySQLApartmentDao has problem with Sql in update method",e);
        }catch (ConnectionPoolException e) {
            throw new DaoException("MySQLApartmentDao has problem with connection pool in update method",e);
        }
        finally {
            ContextServletListener.connectionPool.closeConnection(connection, preparedStatement);
        }
    }
    public void delete (Apartment apartment)throws DaoException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try{
            connection =  ContextServletListener.connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(deleteById);
            preparedStatement.setInt(1,apartment.getId());
            preparedStatement.execute();

        }catch (SQLException e){
            throw new DaoException("MySQLApartmentDao has problem with Sql in delete method",e);
        }catch (ConnectionPoolException e) {
            throw new DaoException("MySQLApartmentDao has problem with connection pool in delete method",e);
        }
        finally {
            ContextServletListener.connectionPool.closeConnection(connection, preparedStatement);
        }
    }
    public boolean find(Apartment apartment)throws DaoException{
        boolean result = false;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet =null;
        try{
            connection =  ContextServletListener.connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(findApartment);
            preparedStatement.setInt(1,apartment.getPrice());
            preparedStatement.setInt(2,apartment.getCouchette());
            preparedStatement.setInt(3,apartment.getRoomNumber());
            preparedStatement.setInt(4,apartment.getClassId());
            resultSet = preparedStatement.executeQuery();
            result = resultSet.next();

        }catch (SQLException e){
            throw new DaoException("MySQLApartmentDao has problem with Sql in getById mathod",e);
        }catch (ConnectionPoolException e) {
            throw new DaoException("MySQLApartmentDao has problem with connection pool in getById mathod",e);
        }
        finally {
            ContextServletListener.connectionPool.closeConnection(connection, preparedStatement,resultSet);
        }
        return result;
    }
    public Map<Integer,Apartment> getSuitableApartmentMap(Apartment apartment)throws DaoException{
        Map<Integer, Apartment> mapApartment = new LinkedHashMap<Integer, Apartment>();
        PreparedStatement preparedStatement = null;
        Connection connection =null;
        ResultSet resultSet =null;
        try{
            connection = ContextServletListener.connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(getApByCouchClassidRoom);
            preparedStatement.setInt(1, apartment.getClassId());
            preparedStatement.setInt(2, apartment.getCouchette());
            preparedStatement.setInt(3, apartment.getRoomNumber());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt(DataBaseParameterName.ID);
                int price = resultSet.getInt(DataBaseParameterName.PRICE);
                int couchette = resultSet.getInt(DataBaseParameterName.COUCHETTE);
                int room_number = resultSet.getInt(DataBaseParameterName.ROOM_NUMBER);
                String status = resultSet.getString(DataBaseParameterName.STATUS);
                int class_id = resultSet.getInt(DataBaseParameterName.CLASS_ID);

                Apartment apartment1 = new Apartment();
                apartment1.setId(id);
                apartment1.setPrice(price);
                apartment1.setCouchette(couchette);
                apartment1.setRoomNumber(room_number);
                apartment1.setStatus(status);
                apartment1.setClassId(class_id);
                mapApartment.put(id, apartment1);
            }

        }catch (SQLException e){
            throw new DaoException("MySQLApartmentDao has problem with Sql in getSuitableApartmentMap method",e);
        }catch (ConnectionPoolException e) {
            throw new DaoException("MySQLApartmentDao has problem with connection pool in getSuitableApartmentMap method",e);
        } finally {
            ContextServletListener.connectionPool.closeConnection(connection, preparedStatement,resultSet);
        }
        return mapApartment;
    }
    public Map<Integer,Apartment> getAllApartmentMap()throws DaoException{
        Map<Integer, Apartment> mapApartment = new LinkedHashMap<Integer, Apartment>();
        PreparedStatement preparedStatement = null;
        Connection connection =null;
        ResultSet resultSet =null;
        try{
            connection = ContextServletListener.connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(getAllApartments);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt(DataBaseParameterName.ID);
                int price = resultSet.getInt(DataBaseParameterName.PRICE);
                int couchette = resultSet.getInt(DataBaseParameterName.COUCHETTE);
                int room_number = resultSet.getInt(DataBaseParameterName.ROOM_NUMBER);
                String status = resultSet.getString(DataBaseParameterName.STATUS);
                int class_id = resultSet.getInt(DataBaseParameterName.CLASS_ID);

                Apartment apartment1 = new Apartment();
                apartment1.setId(id);
                apartment1.setPrice(price);
                apartment1.setCouchette(couchette);
                apartment1.setRoomNumber(room_number);
                apartment1.setStatus(status);
                apartment1.setClassId(class_id);
                mapApartment.put(id, apartment1);
            }

        }catch (SQLException e){
            throw new DaoException("MySQLApartmentDao has problem with Sql in getSuitableApartmentMap method",e);
        }catch (ConnectionPoolException e) {
            throw new DaoException("MySQLApartmentDao has problem with connection pool in getSuitableApartmentMap method",e);
        } finally {
            ContextServletListener.connectionPool.closeConnection(connection, preparedStatement,resultSet);
        }
        return mapApartment;
    }
}
