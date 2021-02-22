package com.revature.orm.util;

import com.revature.orm.util.session.SessionFactory;

import java.io.FileReader;
import java.util.*;

public class Configuration {

    private String dbUrl;
    private String dbUsername;
    private String dbPassword;
    private List<Metamodel<Class<?>>> metamodelList;
    private Properties props = new Properties();

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

    @SuppressWarnings({ "unchecked" })
    public Configuration addAnnotatedClass(Class annotatedClass) {

        if (metamodelList == null) {
            metamodelList = new LinkedList<>();
        }

        metamodelList.add(Metamodel.of(annotatedClass));

        return this;
    }

    public List<Metamodel<Class<?>>> getMetamodels() {
        return (metamodelList == null) ? Collections.emptyList() : metamodelList;
    }

    public SessionFactory buildSessionFactory () {
        return new SessionFactory(metamodelList);
    }

}
