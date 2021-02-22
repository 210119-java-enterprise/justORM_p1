package com.revature.orm.util.session;

import com.revature.orm.dao.DaoModel;
import com.revature.orm.services.DaoService;
import com.revature.orm.util.ConnectionFactory;
import com.revature.orm.util.Metamodel;

import java.util.List;

public class SessionFactory {

    private List<Metamodel<Class<?>>> metamodelList;
    private DaoService daoService;

    public SessionFactory(List<Metamodel<Class<?>>> metamodelList)
    {
        this.metamodelList = metamodelList;
        final DaoModel daoModel = new DaoModel();

        daoService = new DaoService(daoModel);
    }

    public SessionFactory addAnnotatedClass(Class clazz) {
        metamodelList.add(Metamodel.of(clazz));
        return this;
    }

    public Session openSession() {
        Session session = new Session(metamodelList, daoService, ConnectionFactory.getInstance().getConnection());
        return session;
    }

}
