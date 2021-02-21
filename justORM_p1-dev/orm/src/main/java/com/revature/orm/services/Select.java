package com.revature.orm.services;

import com.revature.orm.annotations.Table;
import com.revature.orm.util.Metamodel;

public class Select {
    private String select;

    public Select(Metamodel<?> model) {
        select = "";
        selectAll(model);
    }

    //to be implemented
//    public Select(Metamodel<?> model, String... columnNames){
//        select = "";
//        selectFrom(model, columnNames);
//    }

    private void selectAll(Metamodel<?> model){
        String tableName = model.getModel().getAnnotation(Table.class).tableName();

        select = "SELECT * FROM " + tableName;
    }

    public String getSelect() {
        return select;
    }
}
