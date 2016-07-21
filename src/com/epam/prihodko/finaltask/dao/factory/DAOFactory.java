package com.epam.prihodko.finaltask.dao.factory;
import com.epam.prihodko.finaltask.dao.entity.*;

public abstract class DAOFactory {
    private final static MySQLDAOFactory mySqlDAOFactory = new MySQLDAOFactory();
    /**Enum of DAO types supported by the factory*/
    public static enum DataSourceName{
        MYSQL
    }
    /**
     * There are methods for each DAO that can be created.
     * The concrete factories implement these methods
     * */
    public abstract AccountDao getAccountDao();
    public abstract ApartmentDao getApartmentDao();
    public abstract ApartmentClassDao getApartmentClassDao();
    public abstract CheckDao getCheckDao();
    public abstract OrderDao getOrderDao();
    public abstract PersonDao getPersonDao();
    /**
     * Method that gets needed  DAOFactory
     * */
    public static DAOFactory getDAOFactory(DataSourceName dataSourceName ){
        switch (dataSourceName){
            case MYSQL:
                return mySqlDAOFactory;
            default:
                return null;
        }
    }
}
