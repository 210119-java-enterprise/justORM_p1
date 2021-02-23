package com.revature.orm.util;

import com.revature.orm.annotations.Column;

import java.lang.reflect.Field;

/**
 * this class represents a column in a database
 */
public class ColumnField {

    private Field field;

    /**
     * constructor for the column field
     * @param field the fields of the column in a database
     */
    public ColumnField(Field field) {
        if (field.getAnnotation(Column.class) == null) {
            throw new IllegalStateException("Cannot create ColumnField object! Provided field, " + getName() + "is not annotated with @Column");
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
     * returns the column name from an annotation of a column
     * @return returns the column name
     */
    public String getColumnName() {
        return field.getAnnotation(Column.class).columnName();
    }

}
