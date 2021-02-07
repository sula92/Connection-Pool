package lk.ijse.dep.web.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBCP {

    private int poolSize = 4;
    private List<Connection> connectionPool = new ArrayList<>();
    private List<Connection> consumerPool = new ArrayList<>();

    public DBCP() {
        initializeConnections();
    }

    public DBCP(int poolSize) {
        this.poolSize = poolSize;
        initializeConnections();
    }

    private void initializeConnections() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < poolSize; i++) {
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dep4", "root", "1234");
                connectionPool.add(connection);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public synchronized Connection obtainConnection() {
        while (connectionPool.isEmpty()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Connection connection = connectionPool.get(0);
        consumerPool.add(connection);
        connectionPool.remove(connection);
        return connection;
    }

    public synchronized void releaseConnection(Connection connection) {
        consumerPool.add(connection);
        connectionPool.remove(connection);
        notify();
    }

    public synchronized void releaseAllConnection(){
        for (Connection connection : consumerPool) {
           connectionPool.add(connection);
        }
        consumerPool.clear();
        notifyAll();
    }
}
