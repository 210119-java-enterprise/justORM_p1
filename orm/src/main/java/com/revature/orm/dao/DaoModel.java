package com.revature.orm.dao;

import com.revature.orm.annotations.*;
import com.revature.orm.services.Insert;
import com.revature.orm.util.ConnectionFactory;
import com.revature.orm.util.Metamodel;
import com.revature.orm.util.PrimaryKey;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class DaoModel {

    public int insert(Metamodel<?> model, Object object){
        int result = 0;
        Insert insertStatement = new Insert(model ,object);
        ArrayList<String> objectValues = getValues(object);


        return result;
    }

    private ArrayList<String> getValues(Object o)
    {
        ArrayList<String> objectValues = new ArrayList<>();
        Field[] fields = o.getClass().getDeclaredFields();

        for(Field f: fields)
        {
            f.setAccessible(true); //suppress checks for Java language access control
            Column column = f.getAnnotation(Column.class);
            PK pk = f.getAnnotation(PK.class);
            FK fk = f.getAnnotation(FK.class);

            if(pk != null || fk != null || column != null) {
                try {
                    objectValues.add(f.get(o).toString());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return objectValues;
    }
}
