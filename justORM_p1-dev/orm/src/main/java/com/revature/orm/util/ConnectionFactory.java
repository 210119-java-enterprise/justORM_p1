package com.revature.orm.util;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private static Properties props = new Properties();

    /*
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
     * @param pathName the string representation of the path to your properties file
     */
    public ConnectionFactory(String pathName){
        try{
            props.load(new FileReader(pathName));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Gets the connection to the database
     * @return the connection to the database
     */
    public static Connection getConnection(){
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
