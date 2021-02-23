package com.revature.orm.services;

import com.revature.orm.annotations.Column;
import com.revature.orm.annotations.FK;
import com.revature.orm.annotations.PK;
import com.revature.orm.annotations.Table;
import com.revature.orm.util.ForeignKeyField;
import com.revature.orm.util.Metamodel;
import com.revature.orm.util.PrimaryKey;
import javafx.scene.control.Tab;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * This class builds the insert query string for the postgres insert statement
 */
public class Insert {

    //String for insertion statement
    private String insert="";

    /**
     * Constructor for creating an insert into SQL that takes in an object and a metamodel
     * that is related to that object
     * @param model metamodel of the related object being inserted
     * @param o object that has the values to be inserted
     */
    public Insert(Metamodel<?> model, Object o)
    {
        parseModel(model, o);
    }

    /**
     * Parses the model and object for the table name and columns that is used to build the inserted
     * statement. Also builds the syntax for the insertion using stringbuilder with the data from the table and columns.
     * @param model the metamodel that relates to the object
     * @param o object that has the values to be inserted
     */
    private void parseModel(Metamodel<?> model, Object o) {
        String table = model.getModel().getAnnotation(Table.class).tableName();

        ArrayList<String> columns = new ArrayList<>();

        for(Field f: o.getClass().getDeclaredFields()) {
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

        StringBuilder tableInsert = new StringBuilder("INSERT INTO " + table + " (");
        StringBuilder values = new StringBuilder("VALUES (");

        for (int i = 0; i < numberOfColumns; i++) {
            if(i == (numberOfColumns -1))
            {
                tableInsert.append(columns.get(i)).append(") ");
                values.append(" ? ").append(") ");
            }else {
                tableInsert.append(columns.get(i)).append(", ");
                values.append(" ? ").append(", ");
            }
        }

        insert = tableInsert.toString() + values.toString();
    }

    public String getInsert() {
        return insert;
    }

}
