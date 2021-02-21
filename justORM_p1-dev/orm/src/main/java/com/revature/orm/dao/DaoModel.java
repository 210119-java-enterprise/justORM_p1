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

    public void insert(Metamodel<?> model, Object object){
        int result = 0;
        Insert insertStatement = new Insert(model ,object);
        ArrayList<String> objectValues = getValues(object);

        try {

//            insertStatement = "INSERT INTO user_accounts (user_id, acc_id) "+
//                              "VALUES (?, ?) ";

            Connection con = ConnectionFactory.getInstance.getConnection(); //need to work on connection pooling
            PreparedStatement pstmt = con.prepareStatement(insertStatement.getInsert());

            int j = 1;

            // setting the ? in prep statement
            for (int i = 0; i < objectValues.size(); i++) {
                pstmt.setObject(j, objectValues.get(i));
                j++;
            }
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            con.close();
        }

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
