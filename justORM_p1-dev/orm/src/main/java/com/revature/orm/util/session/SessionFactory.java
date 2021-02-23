package com.revature.orm.util.session;

import com.revature.orm.repos.CRUDRepo;
import com.revature.orm.services.ServiceModel;
import com.revature.orm.util.ConnectionFactory;
import com.revature.orm.util.Metamodel;

import java.util.List;

/**
 * creates a sessions
 */
public class SessionFactory {

    private List<Metamodel<Class<?>>> metamodelList;
    private ServiceModel serviceModel;

    /**
     * constructor for session factor
     * @param metamodelList the meta model passed by configuration
     */
    public SessionFactory(List<Metamodel<Class<?>>> metamodelList)
    {
        this.metamodelList = metamodelList;
        final CRUDRepo CRUDRepo = new CRUDRepo();

        serviceModel = new ServiceModel(CRUDRepo);
    }

    /**
     * adds a annotated class to a meta model list
     * @param clazz annotated class
     * @return returns the session factory
     */
    public SessionFactory addAnnotatedClass(Class clazz) {
        metamodelList.add(Metamodel.of(clazz));
        return this;
    }

    /**
     * creates a session object from the meta model list, service model , and a connection instance
     * @return returns the session that was created
     */
    public Session openSession() {
        Session session = new Session(metamodelList, serviceModel, ConnectionFactory.getInstance().getConnection());
        return session;
    }

}
