package com.revature.orm.services;

import com.revature.orm.dao.DaoModel;
import com.revature.orm.util.Metamodel;

public class DaoService {

    private DaoModel dao;

    public DaoService(DaoModel dao)
    {
        this.dao = dao;
    }

    public void insert(Metamodel<?> model, Object object){
        if(!isObjectValid(object)){
            throw new RuntimeException("Invalid user, user is null");
        }

        dao.insert(model, object);
    }

    public boolean isObjectValid(Object o)
    {
        if(o == null) return false;

        return true;
    }
}
