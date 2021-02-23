package com.revature.orm.repos;

import com.revature.orm.annotations.*;
import com.revature.orm.services.Delete;
import com.revature.orm.services.Insert;
import com.revature.orm.services.Select;
import com.revature.orm.services.Update;
import com.revature.orm.util.ConnectionFactory;
import com.revature.orm.util.Metamodel;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;

/**
 * This class is used to interact with the database with a DML/DQL statement
 * and will return an a result stating that the statement was successful or not
 */
public class CRUDRepo {

    /**
     * Insert a user specified object into a already existing database
     * @param model the model of the class inserted
     * @param object the object of with values inserted
     * @return returns a result of 0 if not inserted correctly, or 1 if insert worked
     */
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

            System.out.println(pstmt);

            result = pstmt.executeUpdate(); //1 if successful insert | 0 if not inserted

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
////        finally {
//            con.close();
//        }

        return result;
    }

    /**
     * delete a user specified object from a already existing database where the conditions
     * is equals to the value from the object in the database
     * @param model the model of the class deleted
     * @param object the object of with values deleted
     * @return returns a result of 0 if not deleted correctly, or 1 if deleted worked
     */
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

            System.out.println(pstmt);
            result = pstmt.executeUpdate();//1 if successful  | 0 if not
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * selects all the objects within a table given the table name from an object
     * @param model the model of the class deleted
     * @param obj the object of with values deleted
     * @return returns a list of records inside a database
     */
    public List<?> selectAll(Metamodel<?> model, Object obj){

        List<Object> objects = new ArrayList<>();
        Select selectString = new Select(model);

        try {
//            syntax: SELECT * FROM tablename

            Connection con = ConnectionFactory.getInstance().getConnection();
            PreparedStatement pstmt = con.prepareStatement(selectString.getSelect());

            ResultSet rs = pstmt.executeQuery();

            System.out.println(pstmt);

            ResultSetMetaData rsData = rs.getMetaData();
            objects = mapping(model, rs, obj, rsData);

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return objects;
    }

    /**
     * selects all the objects within a table given the table name from an object
     * @param model the model of the class updated
     * @param newObject the object of which values is to be inserted into the old object
     * @param updatingObject the object that we want to update
     * @return returns a 1 if successfully updated else returns a 0 if update failed
     */
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

            System.out.println(pstmt);

            result = pstmt.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * parse through the object passed by the user and get the values within the object
     * @param o object being parse to get the values
     * @return returns a array list of values contained in the object
     */
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

    /**
     * maps the data from a database to a list of objects
     * @param model the model of the object
     * @param rs the result set returned from the database
     * @param obj the object of the list
     * @param rsData the meta data from the result set
     * @return returns a list of objects acquired from the database
     */
    private List<Object> mapping(Metamodel<?> model, ResultSet rs, Object obj, ResultSetMetaData rsData)
    {
        List<Object> objects= new LinkedList<>();

        try {
            List<String> rsColumns = new LinkedList<>();

            for (int i = 0; i < rsData.getColumnCount(); i++) {
                rsColumns.add(rsData.getColumnName(i + 1)); //column – the first column is 1, the second is 2...
            }

            System.out.println(rsColumns);

            while (rs.next()) {

                Object newObject = obj.getClass().getConstructor().newInstance();

                for (String c : rsColumns) {

                    Class<?> classType = model.findClassOfColumn(c);
                    Object objectValue = rs.getObject(c);

                    String colName = model.findFieldNameOfColumn(c);
                    String methodName = colName.substring(0,1).toUpperCase() + colName.substring(1);

                    Method method = obj.getClass().getMethod("set" + methodName, classType);
                    method.invoke(newObject, objectValue);

                }
                objects.add(newObject);
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

        System.out.println(objects);

        return objects;
    }


}




