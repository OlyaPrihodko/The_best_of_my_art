package com.epam.prihodko.finaltask.dao.connection;

import com.epam.prihodko.finaltask.exception.daoException.ConnectionPoolException;
import org.apache.log4j.*;
import java.sql.*;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

public final class ConnectionPool {
    private final static Logger logger = Logger.getLogger("ConnectionPool");
    private static volatile ConnectionPool instance;
    private final static String useUnicode = "useUnicode";
    private final static String characterEncoding = "characterEncoding";
    private final static String useUnicodeValue = "true";
    private final static String characterEncodingValue = "UTF-8";

    private BlockingQueue<Connection> connectionQueue;
    private BlockingQueue<Connection> givenAwayConQueue;
    private String driverName;
    private String url;
    private String user;
    private String password;
    private int poolSizeMax;
    private int poolSizeMin;

    public static ConnectionPool getInstance() {
        ConnectionPool localInstance = instance;
        if (localInstance == null) {
            synchronized (ConnectionPool.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ConnectionPool();
                }
            }
        }
        return localInstance;
    }
    private ConnectionPool() {
        DBResourceManager dbResourseManager = DBResourceManager.getInstance();
        this.driverName = dbResourseManager.getValue(DBParameter.DB_DRIVER);
        this.url = dbResourseManager.getValue(DBParameter.DB_URL);
        this.user = dbResourseManager.getValue(DBParameter.DB_USER);
        this.password = dbResourseManager.getValue(DBParameter.DB_PASSWORD);
        try {
            this.poolSizeMax = Integer.parseInt(dbResourseManager.getValue(DBParameter.DB_POLL_SIZE_MAX));
            this.poolSizeMin = Integer.parseInt(dbResourseManager.getValue(DBParameter.DB_POLL_SIZE_MIN));
        } catch (NumberFormatException e) {
            poolSizeMax = 15;
            poolSizeMin = 5;
        }
    }
    public void initPoolData() throws ConnectionPoolException {
        Locale.setDefault(Locale.ENGLISH);
        try {
            Class.forName(driverName);
            Properties properties = new Properties();
            properties.setProperty(useUnicode,useUnicodeValue);
            properties.setProperty(characterEncoding,characterEncodingValue);
            givenAwayConQueue = new ArrayBlockingQueue<Connection>(poolSizeMax);
            connectionQueue = new ArrayBlockingQueue<Connection>(poolSizeMax);
            for (int i = 0; i < poolSizeMin; i++) {
                Connection connection = DriverManager.getConnection(url,user,password);
                PooledConnection pooledConnection = new PooledConnection(connection);
                connectionQueue.add(pooledConnection);
            }
        } catch (SQLException e) {
            throw new ConnectionPoolException("SQLException in ConnectionPool", e);

        } catch (ClassNotFoundException e) {
            throw new ConnectionPoolException("Can't find database driver class", e);
        }

    }
    public void dispose() {
        clearConnectionQueue();
    }
    public void clearConnectionQueue() {
        try {
        closeConnectionsQueue(givenAwayConQueue);
        closeConnectionsQueue(connectionQueue);
    } catch (SQLException e) {
	    logger.info(Level.ERROR + "Error closing the connection." + e);
    }
    }
    public Connection takeConnection() throws ConnectionPoolException {
        Connection connection = null;
        try {
            if(connectionQueue.size()==0&&givenAwayConQueue.remainingCapacity()>0){
                try {
                    connection = DriverManager.getConnection(url, user, password);
                    PooledConnection pooledConnection = new PooledConnection(connection);
                    connectionQueue.add(pooledConnection);
                    connection = connectionQueue.take();
                    givenAwayConQueue.add(connection);
                    logger.info("connection "+connection+" created");
                } catch (SQLException e) {
                    throw new ConnectionPoolException("Error connecting to the data source for create new connection", e);
                }
            }
            else{
                connection = connectionQueue.take();
                givenAwayConQueue.add(connection);
                logger.info("connection "+connection+" created");
            }
        } catch (InterruptedException e) {
            throw new ConnectionPoolException("Error connecting to the data source.", e);
        }
        return connection;

    }
    public void closeConnection(Connection con, Statement st, ResultSet rs) {
        try {
        con.close();
    } catch (SQLException e) {
        logger.info(Level.ERROR + " Connection isn't return to thepool. " + e);
    }
        try {
            rs.close();
        } catch (SQLException e) {
            logger.info(Level.ERROR + " ResultSet isn't closed. " + e);
        }
        try {
            st.close();
        } catch (SQLException e) {
            logger.info(Level.ERROR + " Statement isn't closed. " + e);
        }
    }
    public void closeConnection(Connection con, Statement st) {
        try {
        con.close();
    } catch (SQLException e) {
            logger.info(Level.ERROR+" Connection isn't return to the pool. "+e);
    }try {
            st.close();
        } catch (SQLException e) {
            logger.info(Level.ERROR+" Statement isn't closed. "+e);
        }
    }
    public void closeConnectionsQueue(BlockingQueue<Connection> queue) throws SQLException {
        Connection connection;
        while ((connection = queue.poll()) != null) {
            if (!connection.getAutoCommit()) {
            connection.commit();
        }
            ((PooledConnection) connection).reallyClose();
        }
    }
    public int getpoolSizeMax(){
        return poolSizeMax;
    }
    public int getpoolSizeMin(){
        return poolSizeMin;
    }
    public int getCurrSizeConnQueue(){
        return connectionQueue.size();
    }
    public int getCurrSizeGivenAwayConnQueue(){
        return givenAwayConQueue.size();
    }
    private class PooledConnection implements Connection {
        private Connection connection;

        public PooledConnection(Connection c) throws SQLException {
            this.connection = c;
            this.connection.setAutoCommit(true);

        }
        public void reallyClose() throws SQLException {
            connection.close();
        }
        @Override
        public void clearWarnings() throws SQLException {
            connection.clearWarnings();
        }
        @Override
        public void close() throws SQLException {
            if (connection.isClosed()) {
            throw new SQLException("Attempting to close closedconnection.");
        }

            if (connection.isReadOnly()) { connection.setReadOnly(false);
            }

            if (!givenAwayConQueue.remove(this)) { throw new SQLException(

                    "Error deleting connection from the given awayconnections pool.");
            }

            if (!connectionQueue.offer(this)) { throw new SQLException(
                    "Error allocating connection in the pool.");
            }

        }
        @Override
        public void commit() throws SQLException {
            connection.commit();
        }
        @Override
        public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
            return connection.createArrayOf(typeName, elements);

        }
        @Override
        public Blob createBlob() throws SQLException { return connection.createBlob();

        }
        @Override
        public Clob createClob() throws SQLException { return connection.createClob();

        }
        @Override
        public NClob createNClob() throws SQLException { return connection.createNClob();

        }
        @Override
        public SQLXML createSQLXML() throws SQLException { return connection.createSQLXML();

        }
        @Override
        public Statement createStatement() throws SQLException { return connection.createStatement();

        }
        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException { return connection.createStatement(resultSetType,

                resultSetConcurrency);

        }
        @Override
        public Statement createStatement(int resultSetType,int resultSetConcurrency, int resultSetHoldability) throws SQLException {

            return connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
        }
        @Override
        public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
            return connection.createStruct(typeName, attributes);

        }
        @Override
        public boolean getAutoCommit() throws SQLException { return connection.getAutoCommit();

        }
        @Override
        public String getCatalog() throws SQLException { return connection.getCatalog();

        }
        @Override
        public Properties getClientInfo() throws SQLException { return connection.getClientInfo();

        }
        @Override
        public String getClientInfo(String name) throws SQLException { return connection.getClientInfo(name);

        }
        @Override
        public int getHoldability() throws SQLException { return connection.getHoldability();

        }
        @Override
        public DatabaseMetaData getMetaData() throws SQLException { return connection.getMetaData();

        }
        @Override
        public int getTransactionIsolation() throws SQLException { return connection.getTransactionIsolation();

        }
        @Override
        public Map<String, Class<?>> getTypeMap() throws SQLException { return connection.getTypeMap();

        }
        @Override
        public SQLWarning getWarnings() throws SQLException { return connection.getWarnings();

        }
        @Override
        public boolean isClosed() throws SQLException { return connection.isClosed();

        }
        @Override
        public boolean isReadOnly() throws SQLException { return connection.isReadOnly();

        }
        @Override
        public boolean isValid(int timeout) throws SQLException { return connection.isValid(timeout);

        }
        @Override
        public String nativeSQL(String sql) throws SQLException { return connection.nativeSQL(sql);

        }
        @Override
        public CallableStatement prepareCall(String sql) throws SQLException { return connection.prepareCall(sql);

        }
        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {

            return connection.prepareCall(sql, resultSetType, resultSetConcurrency);
        }
        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {

            return connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }
        @Override
        public PreparedStatement prepareStatement(String sql) throws SQLException {
            return connection.prepareStatement(sql);

        }
        @Override
        public PreparedStatement prepareStatement(String sql,int autoGeneratedKeys) throws SQLException {

            return connection.prepareStatement(sql, autoGeneratedKeys);

        }
        @Override
        public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
            return connection.prepareStatement(sql, columnIndexes);

        }
        @Override
        public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
            return connection.prepareStatement(sql, columnNames);
        }
        @Override
        public PreparedStatement prepareStatement(String sql,int resultSetType, int resultSetConcurrency) throws SQLException {

            return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);

        }
        @Override
        public PreparedStatement prepareStatement(String sql,int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {

            return connection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }
        @Override
        public void rollback() throws SQLException { connection.rollback();
        }
        @Override
        public void setAutoCommit(boolean autoCommit) throws SQLException { connection.setAutoCommit(autoCommit);
        }
        @Override
        public void setCatalog(String catalog) throws SQLException { connection.setCatalog(catalog);
        }
        @Override
        public void setClientInfo(String name, String value) throws SQLClientInfoException {

            connection.setClientInfo(name, value);

        }
        @Override
        public void setHoldability(int holdability) throws SQLException { connection.setHoldability(holdability);
        }
        @Override
        public void setReadOnly(boolean readOnly) throws SQLException { connection.setReadOnly(readOnly);
        }
        @Override
        public Savepoint setSavepoint() throws SQLException { return connection.setSavepoint();

        }
        @Override
        public Savepoint setSavepoint(String name) throws SQLException { return connection.setSavepoint(name);

        }
        @Override
        public void setTransactionIsolation(int level) throws SQLException { connection.setTransactionIsolation(level);
        }
        @Override
        public boolean isWrapperFor(Class<?> iface) throws SQLException { return connection.isWrapperFor(iface);

        }
        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException { return connection.unwrap(iface);

        }
        @Override
        public void abort(Executor arg0) throws SQLException { connection.abort(arg0);

        }
        @Override
        public int getNetworkTimeout() throws SQLException { return connection.getNetworkTimeout();

        }
        @Override
        public String getSchema() throws SQLException { return connection.getSchema();

        }
        @Override
        public void releaseSavepoint(Savepoint arg0) throws SQLException { connection.releaseSavepoint(arg0);
        }
        @Override
        public void rollback(Savepoint arg0) throws SQLException { connection.rollback(arg0);
        }
        @Override
        public void setClientInfo(Properties arg0) throws SQLClientInfoException {
            connection.setClientInfo(arg0);

        }
        @Override
        public void setNetworkTimeout(Executor arg0, int arg1) throws SQLException {

            connection.setNetworkTimeout(arg0, arg1);

        }
        @Override
        public void setSchema(String arg0) throws SQLException { connection.setSchema(arg0);
        }
        @Override
        public void setTypeMap(Map<String, Class<?>> arg0) throws SQLException { connection.setTypeMap(arg0);
        }
    }

}
