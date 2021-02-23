package com.revature.orm.services;

import com.revature.orm.annotations.Table;
import com.revature.orm.util.Metamodel;

/**
 * This class builds the select query string for the postgres select statement
 */
public class Select {
    private String select="";

    /**
     * Constructor for creating an select * into SQL that takes in an metamodel
     * that is related to that object
     * @param model metamodel of the related object being selected
     */
    public Select(Metamodel<?> model) {
        selectAll(model);
    }

    //to be implemented
//    public Select(Metamodel<?> model, String... columnNames){
//        select = "";
//        selectFrom(model, columnNames);
//    }

    /**
     * Parses the model for the table name that is used to build the select *
     * statement. Also builds the syntax for the selection using stringbuilder with the table name.
     * @param model the metamodel that relates to the object
     */
    private void selectAll(Metamodel<?> model){
        String tableName = model.getModel().getAnnotation(Table.class).tableName();

        select = "SELECT * FROM " + tableName;
    }

    public String getSelect() {
        return select;
    }
}
