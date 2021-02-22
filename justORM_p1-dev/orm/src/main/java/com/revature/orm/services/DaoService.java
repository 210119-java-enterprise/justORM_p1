package com.revature.orm.services;

import com.revature.orm.dao.DaoModel;
import com.revature.orm.util.Metamodel;

import java.util.List;

public class DaoService {

    private DaoModel dao;

    public DaoService(DaoModel dao)
    {
        this.dao = dao;
    }

    public int insert(Metamodel<?> model, Object object){
        if(!isObjectValid(object)){
            throw new RuntimeException("Invalid object, object is null");
        }

        return dao.insert(model, object);
    }

    public int delete(Metamodel<?> model, Object object) {
        if(!isObjectValid(object))
        {
            throw new RuntimeException("Invalid object, object is null");
        }

        return dao.delete(model, object);
    }

    public List<?> selectAll(Metamodel<?> model) {
        return dao.selectAll(model);
    }

    public int update(Metamodel<?> model, Object newObj, Object oldObj) {
        if(!isObjectValid(newObj))
        {
            throw new RuntimeException("Invalid object, object is null");
        }

        return dao.update(model, newObj, oldObj);
    }

    public boolean isObjectValid(Object o)
    {
        if(o == null) return false;

        return true;
    }
}
