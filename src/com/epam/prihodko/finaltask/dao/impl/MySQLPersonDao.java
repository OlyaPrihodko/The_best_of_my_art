package com.epam.prihodko.finaltask.dao.impl;


import com.epam.prihodko.finaltask.controller.listener.ContextServletListener;
import com.epam.prihodko.finaltask.dao.DataBaseParameterName;
import com.epam.prihodko.finaltask.dao.entity.PersonDao;
import com.epam.prihodko.finaltask.entity.Person;
import com.epam.prihodko.finaltask.exception.daoException.ConnectionPoolException;
import com.epam.prihodko.finaltask.exception.daoException.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLPersonDao implements PersonDao {
    private final static String getById = "select * from person where id=?";
    private final static String createPerson = "insert into person (name, surname, email, phone, account_id) values(?,?,?,?,?)";
    private final static String updatePerson = "update person set name=?, surname=?, email=?, phone=? where id=?";
    private final static String getByAccountId = "select * from person where account_id=?";

    public Person getById(int domainId)throws DaoException {
        Person person = null;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet =null;
        try{
            connection =  ContextServletListener.connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(getById);
            preparedStatement.setInt(1,domainId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String name = resultSet.getString(DataBaseParameterName.NAME);
                String surname = resultSet.getString(DataBaseParameterName.SURNAME);
                String email = resultSet.getString(DataBaseParameterName.EMAIL);
                String phone = resultSet.getString(DataBaseParameterName.PHONE);
                person = new Person(name,surname,email,phone);
            }

        }catch (SQLException e){
            throw new DaoException("MySQLPersonDao has problem with Sql in getById method",e);
        }catch (ConnectionPoolException e) {
            throw new DaoException("MySQLPersonDao has problem with connection pool in getById method",e);
        }
        finally {
            ContextServletListener.connectionPool.closeConnection(connection, preparedStatement,resultSet);
        }
    return person;
    }
    public void create(Person person)throws DaoException{
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        String str = createPerson;
        try{
            connection =  ContextServletListener.connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(str);
            preparedStatement.setString(1,person.getName());
            preparedStatement.setString(2,person.getSurname());
            preparedStatement.setString(3,person.getEmail());
            preparedStatement.setString(4,person.getPhone());
            preparedStatement.setInt(5,person.getAccountId());
            preparedStatement.execute();

        }catch (SQLException e){
            throw new DaoException("MySQLPersonDao has problem with Sql in create method",e);
        }catch (ConnectionPoolException e) {
            throw new DaoException("MySQLPersonDao has problem with connection pool in create method",e);
        }
        finally {
            ContextServletListener.connectionPool.closeConnection(connection, preparedStatement);
        }
    }
    public void update(Person person)throws DaoException{
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        String str = updatePerson;
        try{
            connection =  ContextServletListener.connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(str);
            preparedStatement.setString(1, person.getName());
            preparedStatement.setString(2,person.getSurname());
            preparedStatement.setString(3, person.getEmail());
            preparedStatement.setString(4,person.getPhone());
            preparedStatement.setInt(5,person.getId());
            preparedStatement.execute();

        }catch (SQLException e){
            throw new DaoException("MySQLPersonDao has problem with Sql in update method",e);
        }catch (ConnectionPoolException e) {
            throw new DaoException("MySQLPersonDao has problem with connection pool in update method",e);
        }
        finally {
            ContextServletListener.connectionPool.closeConnection(connection, preparedStatement);
        }
    }
    public void delete(Person person)throws DaoException{}
    public Person getByAccountId(int domainId)throws DaoException {
        Person person = null;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet =null;
        try{
            connection = ContextServletListener.connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(getByAccountId);
            preparedStatement.setInt(1,domainId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String name = resultSet.getString(DataBaseParameterName.NAME);
                String surname = resultSet.getString(DataBaseParameterName.SURNAME);
                String email = resultSet.getString(DataBaseParameterName.EMAIL);
                String phone = resultSet.getString(DataBaseParameterName.PHONE);
                int id = resultSet.getInt(DataBaseParameterName.ID);
                person = new Person(name,surname,email,phone);
                person.setAccountId(domainId);
                person.setId(id);
            }

        }catch (SQLException e){
            throw new DaoException("MySQLPersonDao has problem with Sql in getByAccountId method",e);
        }catch (ConnectionPoolException e) {
            throw new DaoException("MySQLPersonDao has problem with connection pool in getByAccountId method",e);
        }
        finally {
            ContextServletListener.connectionPool.closeConnection(connection, preparedStatement,resultSet);
        }
        return person;
    }
}
