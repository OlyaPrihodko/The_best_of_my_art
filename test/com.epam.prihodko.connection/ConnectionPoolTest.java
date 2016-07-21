package com.epam.prihodko.connection;

import com.epam.prihodko.finaltask.dao.connection.ConnectionPool;
import org.testng.Assert;
import org.testng.annotations.*;
import java.sql.*;

public class ConnectionPoolTest extends Assert {
    private final static ConnectionPool connectionPool = ConnectionPool.getInstance();
    private Connection connection=null;
    @BeforeSuite
    public void beforeSuite()throws Exception{
        connectionPool.initPoolData();
    }
    @Test
    public void checkMinNumberOfConnInPool() throws Exception{
        for(int i=0;i<connectionPool.getpoolSizeMin();i++){
            connection = connectionPool.takeConnection();
        }
        assertEquals("0 5", connectionPool.getCurrSizeConnQueue() + " " + connectionPool.getCurrSizeGivenAwayConnQueue());
    }
    @Test(dependsOnMethods={"checkMinNumberOfConnInPool"})
    public void takeOneMoreConn()throws Exception{
        connection = connectionPool.takeConnection();
        assertEquals("0 6", connectionPool.getCurrSizeConnQueue() + " " + connectionPool.getCurrSizeGivenAwayConnQueue());
    }
    @Test(dependsOnMethods={"takeOneMoreConn"})
    public void closeConnection()throws Exception{
            connection.close();
        assertEquals("1 5",connectionPool.getCurrSizeConnQueue()+" "+connectionPool.getCurrSizeGivenAwayConnQueue());
    }
    @Test(dependsOnMethods={"closeConnection"})
    public void takeMaxNumbOfConnInPool()throws Exception{
        int max=connectionPool.getpoolSizeMax();
        int min=connectionPool.getpoolSizeMin();
        for(int i=0;i<max-min;i++){
            connection = connectionPool.takeConnection();
        }
        assertEquals("0 15",connectionPool.getCurrSizeConnQueue()+" "+connectionPool.getCurrSizeGivenAwayConnQueue());
    }
    @Test( enabled = false, timeOut = 3000)
    public void overflowPool()throws Exception{
        connection = connectionPool.takeConnection();
    }
    @AfterSuite
    public void afterSuite(){
        connectionPool.dispose();
    }
}
