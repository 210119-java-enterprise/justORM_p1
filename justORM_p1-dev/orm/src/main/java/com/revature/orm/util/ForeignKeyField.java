package com.revature.orm.util;

import com.revature.orm.annotations.FK;

import java.lang.reflect.Field;

public class ForeignKeyField {

    private Field field;

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

    public String getColumnName() {
        return field.getAnnotation(FK.class).columnName();
    }

}
