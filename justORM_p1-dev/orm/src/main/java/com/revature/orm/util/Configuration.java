package com.revature.orm.util;

import com.revature.orm.util.session.SessionFactory;

import java.io.FileReader;
import java.util.*;

/**
 * Configuration class for configuring the connection to the database
 */
public class Configuration {

    private String dbUrl;
    private String dbUsername;
    private String dbPassword;
    private List<Metamodel<Class<?>>> metamodelList;
    private Properties props = new Properties();

    /**
     * creates a constructor that loads a application.properties file from the path
     * "src/main/resources/application.properties" and sets the user's authentication to
     * the database
     */
    public Configuration() {
        try {
            props.load(new FileReader("src/main/resources/application.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        dbUrl = props.getProperty("url");
        dbUsername = props.getProperty("username");
        dbPassword = props.getProperty("password");
    }

    /**
     * creates a list and adds a annotated class to the meta model list
     * @param annotatedClass the class added
     * @return returns the configuration object
     */
    @SuppressWarnings({ "unchecked" })
    public Configuration addAnnotatedClass(Class annotatedClass) {

        if (metamodelList == null) {
            metamodelList = new LinkedList<>();
        }

        metamodelList.add(Metamodel.of(annotatedClass));

        return this;
    }

    /**
     * gets the list of meta models for the classes in annotated class
     * @return returns a list of meta models or empty list
     */
    public List<Metamodel<Class<?>>> getMetamodels() {
        return (metamodelList == null) ? Collections.emptyList() : metamodelList;
    }

    /**
     * builds a session factory object with meta models
     * @return returns a new session factory
     */
    public SessionFactory buildSessionFactory () {
        return new SessionFactory(metamodelList);
    }

}
