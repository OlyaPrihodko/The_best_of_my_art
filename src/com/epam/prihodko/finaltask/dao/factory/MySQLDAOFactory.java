package com.epam.prihodko.finaltask.dao.factory;

import com.epam.prihodko.finaltask.dao.entity.*;
import com.epam.prihodko.finaltask.dao.impl.*;
/**
 * Concrete factory
 * */
public class MySQLDAOFactory extends DAOFactory {

    public AccountDao getAccountDao(){
        return new MySQLAccountDao();
    }
    public ApartmentDao getApartmentDao(){
        return new MySQLApartmentDao();
    }
    public ApartmentClassDao getApartmentClassDao(){
        return new MySQLApartmentClassDao();
    }
    public CheckDao getCheckDao(){
        return new MySQLCheckDao();
    }
    public  OrderDao getOrderDao(){
        return new MySQLOrderDao();
    }
    public  PersonDao getPersonDao(){
        return new MySQLPersonDao();
    }
}
