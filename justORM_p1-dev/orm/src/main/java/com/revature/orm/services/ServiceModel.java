package com.revature.orm.services;

import com.revature.orm.repos.CRUDRepo;
import com.revature.orm.util.Metamodel;

import java.util.List;

/**
 * this class acts as a handles for basic CRUD SQL queries that takes in the
 * user input. This class is to assert that the user is passing valid models and objects
 * to the CRUDRepo.
 */
public class ServiceModel {

    private CRUDRepo crudRepo;

    /**
     * creates a new service model
     * @param crudRepo the repo that handles the sql query
     */
    public ServiceModel(CRUDRepo crudRepo)
    {
        this.crudRepo = crudRepo;
    }

    /**
     * Checks the object passed in and insert it into tha database base off
     * of the metamodel
     * @param model the meta model of the object
     * @param object the object whose values are to be inserted
     * @return returns a 1 if successfully inserted or 0 if inserted incorrectly
     */
    public int insert(Metamodel<?> model, Object object){
        if(!isObjectValid(object)){
            throw new RuntimeException("Invalid object, object is null");
        }

        return crudRepo.insert(model, object);
    }

    /**
     * Checks the object passed in and delete it from tha database base off
     * of the metamodel
     * @param model the meta model of the object
     * @param object the object whose values are to be deleted
     * @return returns a 1 if successfully deleted or 0 if deleted incorrectly
     */
    public int delete(Metamodel<?> model, Object object) {
        if(!isObjectValid(object))
        {
            throw new RuntimeException("Invalid object, object is null");
        }

        return crudRepo.delete(model, object);
    }

    /**
     * Checks the object passed in and selects from the database base off
     * of the metamodel
     * @param model the meta model of the object
     * @param object the object whose table name is used to get the table from the database
     * @return returns a list  of objects inside a database
     */
    public List<?> selectAll(Metamodel<?> model, Object object) {
        if(!isObjectValid(object))
        {
            throw new RuntimeException("Invalid object, object is null");
        }
        return crudRepo.selectAll(model, object);
    }

    /**
     * Checks the object passed in and update it into tha database base off
     * of the metamodel and the old object passed in
     * @param model the meta model of the object
     * @param newObj the object whose values are to be newly inserted
     * @param oldObj the object whose values are to be updated
     * @return returns a 1 if successfully updated or 0 if updated incorrectly
     */
    public int update(Metamodel<?> model, Object newObj, Object oldObj) {
        if(!isObjectValid(newObj))
        {
            throw new RuntimeException("Invalid object, object is null");
        }

        return crudRepo.update(model, newObj, oldObj);
    }

    /**
     * checks to see if the object passed in is nulled
     * @param o object to check if nulled
     * @return returns true if object is null else returns false
     */
    public boolean isObjectValid(Object o)
    {
        if(o == null) return false;

        return true;
    }
}
