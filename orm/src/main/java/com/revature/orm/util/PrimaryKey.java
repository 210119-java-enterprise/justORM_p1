package com.revature.orm.util;

import com.revature.orm.annotations.Column;
import com.revature.orm.annotations.PK;

import java.lang.reflect.Field;

public class PrimaryKey {

    private Field field;

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

    public String getColumnName() {
        return field.getAnnotation(PK.class).columnName();
    }

}
