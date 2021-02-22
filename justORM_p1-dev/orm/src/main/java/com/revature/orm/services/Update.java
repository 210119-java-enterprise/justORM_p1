package com.revature.orm.services;

import com.revature.orm.annotations.Column;
import com.revature.orm.annotations.FK;
import com.revature.orm.annotations.PK;
import com.revature.orm.annotations.Table;
import com.revature.orm.util.Metamodel;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Update {

    private String update="";

    public Update(Metamodel<?> model, Object o)
    {
        parseModel(model, o);
    }

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
