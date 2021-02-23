package com.revature.orm.util.session;

import com.revature.orm.exceptions.MisMatchClassException;
import com.revature.orm.exceptions.WrongModelException;
import com.revature.orm.services.ServiceModel;
import com.revature.orm.util.Metamodel;

import java.sql.Connection;
import java.util.List;

/**
 * Abstract the interaction between the JDBC and the user
 *
 * @author Tuan Mai
 */
public class Session {

    private List<Metamodel<Class<?>>> metamodelList;
    private ServiceModel serviceModel;
    private Connection connection;

    /**
     * Constructor for a session
     * @param metamodelList meta model list passed into a session factory
     * @param serviceModel service model to handle JDBC
     * @param connection connection to the database
     */
    public Session (List<Metamodel<Class<?>>> metamodelList, ServiceModel serviceModel, Connection connection)
    {
        this.metamodelList = metamodelList;
        this.serviceModel = serviceModel;
        this.connection = connection;
    }

    /**
     * Inserts a new object into the database
     * @param object the object to be inserted
     * @return returns 1 if insert successful or 0 if insert unsuccessful
     */
    public int save (Object object)
    {
        Metamodel<?> model = isModel(object);

        if(model == null)
        {
            throw new WrongModelException("model is null error.");
        }
        return serviceModel.insert(model, object);
    }

    /**
     * deletes a object into the database
     * @param object the object to be deleted
     * @return returns 1 if delete successful or 0 if delete unsuccessful
     */
    public int delete (Object object)
    {
        Metamodel<?> model = isModel(object);


        return serviceModel.delete(model, object);
    }

    /**
     * Updates a object from the database
     * @param updateObj the object to be newly inserted
     * @param oldObj object to be updated
     * @return returns 1 if update successful or 0 if update unsuccessful
     */
    public int update(Object updateObj, Object oldObj)
    {
        if(!updateObj.getClass().getName().equals(oldObj.getClass().getName())) {
            throw new MisMatchClassException("Classes do not match.");
        }

        Metamodel<?> model = isModel(updateObj);

        return serviceModel.update(model, updateObj, oldObj);
    }

    /**
     * Selects all records from all columns from a specified object class
     * @param object the object of a class that the user wants
     * @return returns a list of the records from the database
     */
    public List<?> selectAll(Object object)
    {
        Metamodel<?> model = isModel(object);

        if(model == null)
        {
            throw new WrongModelException("model is null error.");
        }

        return serviceModel.selectAll(model, object);
    }

    /**
     * checks to see if there is a metamodel of the objects class
     * @param object object being checked
     * @return returns true if there is a metamodel else false
     */
    private Metamodel<?> isModel(Object object)
    {

        for(Metamodel<?> model : metamodelList)
        {
            if(object.getClass().getName().equals(model.getClassName()))
            {
                return model;
            }
        }
        return null;
    }
}
