package com.revature.orm.util;

import com.revature.orm.annotations.Column;
import com.revature.orm.annotations.PK;

import java.lang.reflect.Field;

/**
 * this class represents a primary key in a database
 */
public class PrimaryKey {

    private Field field;

    /**
     * constructor for the column primary key field
     * @param field the pk of the column in a database
     */
    public PrimaryKey(Field field) {
        if (field.getAnnotation(PK.class) == null) {
            throw new IllegalStateException("Cannot create IdField object! Provided field, " + getName() + "is not annotated with @Id");
        }
        this.field = field;
    }

    public String getName() {
        return field.getName();
    }

    public Class<?> getType() {
        return field.getType();
    }

    /**
     * use to get the annotated pk in a object
     * @return returns the field annotated with pk
     */
    public String getColumnName() {
        return field.getAnnotation(PK.class).columnName();
    }

}
