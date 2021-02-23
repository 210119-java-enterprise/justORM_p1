package com.revature.orm.services;

import com.revature.orm.annotations.Column;
import com.revature.orm.annotations.FK;
import com.revature.orm.annotations.PK;
import com.revature.orm.annotations.Table;
import com.revature.orm.util.Metamodel;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * This class builds the update query string for the postgres update statement
 */
public class Update {

    private String update="";

    /**
     * Constructor for updating an inserted object into SQL that takes in an object and a metamodel
     * that is related to that object
     * @param model metamodel of the related object being updated
     * @param o object that has the values to be updated
     */
    public Update(Metamodel<?> model, Object o)
    {
        parseModel(model, o);
    }

    /**
     * Parses the model and object for the table name and columns that is used to build the updated
     * statement. Also builds the syntax for the update using stringbuilder with the data from the table and columns.
     * @param model the metamodel that relates to the object
     * @param o object that has the values to be updated
     */
    private void parseModel(Metamodel<?> model, Object o) {
        String table = model.getModel().getAnnotation(Table.class).tableName();

        ArrayList<String> columns = new ArrayList<>();

        for(Field f: o.getClass().getDeclaredFields()){
            Column column = f.getAnnotation(Column.class);
            PK pk = f.getAnnotation(PK.class);
            FK fk = f.getAnnotation(FK.class);


            if(column != null)
            {
                columns.add(column.columnName());
            }else if(pk != null)
            {
                columns.add(pk.columnName());
            }else if(fk != null)
            {
                columns.add(fk.columnName());
            }

        }

        int numberOfColumns = columns.size();

        StringBuilder condition1 = new StringBuilder("SET ");
        StringBuilder condition2 = new StringBuilder("WHERE ");

        for (int i = 0; i < numberOfColumns; i++) {
            if(i == (numberOfColumns -1))
            {
                condition1.append(columns.get(i)).append(" = ? ");
                condition2.append(columns.get(i)).append(" = ? ");
            }else {
                condition1.append(columns.get(i)).append(" = ? , ");
                condition2.append(columns.get(i)).append(" = ? and ");
            }
        }

        update = "UPDATE " + table + " " + condition1.toString() + condition2.toString();
    }

    public String getUpdate() {
        return update;
    }
}
