package com.epam.prihodko.finaltask.controller.listener;

import com.epam.prihodko.finaltask.dao.connection.ConnectionPool;
import com.epam.prihodko.finaltask.exception.daoException.ConnectionPoolException;
import org.apache.log4j.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * Create and destroyed connectionPool for Data Base
 */
public class ContextServletListener implements ServletContextListener {
    public static ConnectionPool connectionPool=null;
    private static final Logger log = Logger.getLogger(ContextServletListener.class);

    public void contextInitialized(ServletContextEvent event) {
        try{
            connectionPool = ConnectionPool.getInstance();
            connectionPool.initPoolData();
        }
        catch (ConnectionPoolException e) {
        log.error("Exception with connectionPool initialization", e);
        }
    }
    /**
     * Retrieves an Enumeration with all of the currently loaded JDBC drivers.
     * @return the list of JDBC Drivers
     */
    private Enumeration<Driver> getDrivers() {
        return DriverManager.getDrivers();
    }
    /**
     * Unregistering JDBC drivers given as param
     * @param drivers {@link Enumeration} of {@link Driver} to unregister
     */
    private void deregisterDrivers(Enumeration<Driver> drivers) {
        while (drivers.hasMoreElements()) {
            deregisterDriver(drivers.nextElement());
        }
    }
    /**
     * Unregistering single JDBC driver given as param
     * @param driver to unregister
     */
    private void deregisterDriver(Driver driver) {
        try {
            DriverManager.deregisterDriver(driver);
        } catch (SQLException e) {
            log.error("Exception with ContextServletListener in deregisterDriver method",e);
        }
    }
    public void contextDestroyed(ServletContextEvent event){
            connectionPool.dispose();
            deregisterDrivers(getDrivers());
    }
}
