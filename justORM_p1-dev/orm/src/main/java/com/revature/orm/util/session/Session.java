package com.revature.orm.util.session;

import com.revature.orm.dao.DaoModel;
import com.revature.orm.exceptions.MisMatchClassException;
import com.revature.orm.exceptions.WrongModelException;
import com.revature.orm.services.DaoService;
import com.revature.orm.util.Metamodel;

import java.sql.Connection;
import java.util.List;

public class Session {

    private List<Metamodel<Class<?>>> metamodelList;
    private DaoService daoService;
    private Connection connection;

    public Session (List<Metamodel<Class<?>>> metamodelList, DaoService daoService, Connection connection)
    {
        this.metamodelList = metamodelList;
        this.daoService = daoService;
        this.connection = connection;
    }

    public int save (Object object)
    {
        Metamodel<?> model = isModel(object);

        if(model == null)
        {
            throw new WrongModelException("model is null error.");
        }
        return daoService.insert(model, object);
    }

    public int delete (Object object)
    {
        Metamodel<?> model = isModel(object);


        return daoService.delete(model, object);
    }

    public int update(Object updateObj, Object oldObj)
    {
        if(!updateObj.getClass().getName().equals(oldObj.getClass().getName())) {
            throw new MisMatchClassException("Classes do not match.");
        }

        Metamodel<?> model = isModel(updateObj);

        return daoService.update(model, updateObj, oldObj);
    }

    public List<?> selectAll(Class<?> clazz)
    {
        Metamodel<?> model = null;

        for(Metamodel<?> m : metamodelList)
        {
            if(clazz.getName().equals(model.getClassName()))
            {
                model = m;
            }
        }
        if(model == null)
        {
            throw new WrongModelException("model is null error.");
        }

        return daoService.selectAll(model);
    }

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
