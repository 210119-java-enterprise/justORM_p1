package com.revature.orm.dao;

import com.revature.orm.annotations.*;
import com.revature.orm.services.Delete;
import com.revature.orm.services.Insert;
import com.revature.orm.services.Select;
import com.revature.orm.services.Update;
import com.revature.orm.util.ColumnField;
import com.revature.orm.util.ConnectionFactory;
import com.revature.orm.util.Metamodel;
import com.revature.orm.util.PrimaryKey;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class DaoModel {

    public int insert(Metamodel<?> model, Object object){

        int result = 0;
        Insert insertString = new Insert(model,object);
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

        Delete deleteString = new Delete(model,object);
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

    public List<?> selectAll(Metamodel<?> model){

        List<Object> objects = new ArrayList<>();
        Select selectString = new Select(model);

        try {
//            syntax: SELECT * FROM tablename

            Connection con = ConnectionFactory.getInstance().getConnection();
            PreparedStatement pstmt = con.prepareStatement(selectString.getSelect());

            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData rsData = rs.getMetaData();
            objects = mapping(model, rs, rsData);


        } catch (SQLException e) {
            e.printStackTrace();
        }


        return objects;
    }

    public int update(Metamodel<?> model, Object newObject, Object updatingObject){

        int result = 0;

        Update updateString = new Update(model,updatingObject);
        ArrayList<String> updatingObjectValues = getValues(updatingObject);
        ArrayList<String> newObjectValues = getValues(newObject);

        try {

//            updateStatement = "UPDATE bank_accounts SET balance = ?  " +
//                             "WHERE acc_id = ? ";

            Connection con = ConnectionFactory.getInstance().getConnection(); //need to work on connection pooling
            PreparedStatement pstmt = con.prepareStatement(updateString.getUpdate());

            int j = 1;
            int numberColumn = updatingObjectValues.size();

            // setting the ? in prep statement
            for (int i = 0; i < updatingObjectValues.size(); i++) {
                pstmt.setObject(j, newObjectValues.get(i));
                pstmt.setObject(j+numberColumn, updatingObjectValues.get(i));
                j++;
            }
            result = pstmt.executeUpdate();

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

    private List<Object> mapping(Metamodel<?> model, ResultSet rs, ResultSetMetaData rsData)
    {
        List<Object> objects = new ArrayList<>();

        Constructor<?> hasNoArgConstructor = null;
        Constructor<?>[] constructors = model.getModel().getConstructors();

        hasNoArgConstructor = Arrays.stream(constructors).
                             filter(constructor -> constructor.getParameterCount() == 0).
                             findFirst().
                             get();

        try {
            List<String> columns = new ArrayList<>();
            List<ColumnField> columnFields = model.getColumns();

            for (int i = 0; i < rsData.getColumnCount(); i++) {
                columns.add(rsData.getColumnName(i + 1)); //column – the first column is 1, the second is 2...
            }

            while (rs.next()) {

                Object object = hasNoArgConstructor.newInstance();

                for (String c : columns) {
                    for (ColumnField cs : columnFields) {

                        Object objectValue = rs.getObject(c);
                        String colName = cs.getName();

                        String methodName = colName.substring(0,1).toUpperCase() + colName.substring(1);

                        Method method = model.getModel().getMethod("set" + methodName, cs.getType());
                        method.invoke(object, objectValue);
                    }
                }
            }
        }catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return objects;
    }


}




