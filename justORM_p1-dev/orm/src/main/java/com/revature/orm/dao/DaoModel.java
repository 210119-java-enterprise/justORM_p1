package com.revature.orm.dao;

import com.revature.orm.annotations.*;
import com.revature.orm.services.Delete;
import com.revature.orm.services.Insert;
import com.revature.orm.services.Update;
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
        Insert insertString = new Insert(model ,object);
        ArrayList<String> objectValues = getValues(object);

        try {

//            Syntax: insertString = "INSERT INTO tablename (col1, col2) "+
//                                   "VALUES (?, ?) ";

            Connection con = ConnectionFactory.getInstance().getConnection(); //need to work on connection pooling
            PreparedStatement pstmt = con.prepareStatement(insertString.getInsert());

            int j = 1;

            // setting the ? in prep statement
            for (int i = 0; i < objectValues.size(); i++) {
                pstmt.setObject(j, objectValues.get(i));
                j++;
            }
            result = pstmt.executeUpdate(); //1 if successful insert | 0 if not inserted

        } catch (SQLException e) {
            e.printStackTrace();
        }
////        finally {
//            con.close();
//        }

        return result;
    }

    public int delete(Metamodel<?> model, Object object){

        int result = 0;

        Delete deleteString = new Delete(model ,object);
        ArrayList<String> objectValues = getValues(object);

        try {

//          Syntax:  deleteString = "DELETE FROM tablename WHERE col = ?

            Connection con = ConnectionFactory.getInstance().getConnection(); //need to work on connection pooling
            PreparedStatement pstmt = con.prepareStatement(deleteString.getDelete());

            int j = 1;

            // setting the ? in prep statement
            for (int i = 0; i < objectValues.size(); i++) {
                pstmt.setObject(j, objectValues.get(i));
                j++;
            }
            result = pstmt.executeUpdate();//1 if successful  | 0 if not

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void selectAll(Metamodel<?> model, Object object){

        Insert insertStatement = new Insert(model ,object);
        ArrayList<String> objectValues = getValues(object);

        try {

//            insertStatement = "INSERT INTO user_accounts (user_id, acc_id) "+
//                              "VALUES (?, ?) ";

            Connection con = ConnectionFactory.getInstance().getConnection();
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
        }


    }

    public int update(Metamodel<?> model, Object object){

        int result = 0;

        Update updateString = new Update(model ,object);
        ArrayList<String> objectValues = getValues(object);

        try {

//            updateStatement = "UPDATE bank_accounts SET balance = ?  " +
//                             "WHERE acc_id = ? ";

            Connection con = ConnectionFactory.getInstance().getConnection(); //need to work on connection pooling
            PreparedStatement pstmt = con.prepareStatement(updateString.getUpdate());

            int j = 1;

            // setting the ? in prep statement
            for (int i = 0; i < objectValues.size(); i++) {
                pstmt.setObject(j, objectValues.get(i));
                j++;
            }
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
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
