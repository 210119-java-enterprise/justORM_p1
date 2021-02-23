package com.revature.orm.util;

import com.revature.orm.annotations.FK;

import java.lang.reflect.Field;

/**
 * this class represents a foreign key in a database
 */
public class ForeignKeyField {

    private Field field;

    /**
     * constructor for the column foreign key field
     * @param field the fk of the column in a database
     */
    public ForeignKeyField(Field field) {
        if (field.getAnnotation(FK.class) == null) {
            throw new IllegalStateException("Cannot create ForeignKeyField object! Provided field, " + getName() + "is not annotated with @JoinColumn");
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
     * use to get the annotated fk in a object
     * @return returns the field annotated with fk
     */
    public String getColumnName() {
        return field.getAnnotation(FK.class).columnName();
    }

}
