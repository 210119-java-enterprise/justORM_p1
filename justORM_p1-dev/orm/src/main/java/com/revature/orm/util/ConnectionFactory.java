package com.revature.orm.util;

import com.revature.orm.util.session.SessionFactory;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private static Properties props = new Properties();
    private static ConnectionFactory conn = new ConnectionFactory();

    /**
     * Tries to connect to the postgresql class driver to open a connection
     * to the database
     */
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * New connection factory from the path to your properties file
     */
    public ConnectionFactory(){
        try{
            props.load(new FileReader("src/main/resources/application.properties"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static ConnectionFactory getInstance() {return conn;}

    /**
     * Gets the connection to the database
     * @return the connection to the database
     */
    public Connection getConnection(){
        Connection conn = null;

        try{
            conn = DriverManager.getConnection(
                    props.getProperty("url"),
                    props.getProperty("username"),
                    props.getProperty("password"));
        }catch (SQLException e){
            e.printStackTrace();
        }

        return conn;
    }

}
