package com.revature.orm.services;

import com.revature.orm.annotations.Column;
import com.revature.orm.annotations.FK;
import com.revature.orm.annotations.PK;
import com.revature.orm.annotations.Table;
import com.revature.orm.util.Metamodel;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * This class builds the delete query string for the postgres delete statement
 */
public class Delete {

    private String delete="";

    /**
     * Constructor for deleting an inserted object into SQL that takes in an object and a metamodel
     * that is related to that object
     * @param model metamodel of the related object being deleted
     * @param o object that has the values to be deleted
     */
    public Delete(Metamodel<?> model, Object o)
    {
        parseModel(model, o);
    }


    /**
     * Parses the model and object for the table name and columns that is used to build the deleted
     * statement. Also builds the syntax for the deletion using stringbuilder with the data from the table and columns.
     * @param model the metamodel that relates to the object
     * @param o object that has the values to be inserted
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

        StringBuilder condition = new StringBuilder(" WHERE ");

        for (int i = 0; i < numberOfColumns; i++) {
            if(i == (numberOfColumns -1))
            {
                condition.append(columns.get(i)).append(" = ").append(" ? ");
            }else {
                condition.append(columns.get(i)).append(" = ").append(" ? ").append(" and ");
            }
        }

        delete = "DELETE FROM " + table + condition.toString();
    }

    public String getDelete() {
        return delete;
    }
}
