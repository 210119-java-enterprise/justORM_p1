package com.revature.orm.util;

import com.revature.orm.annotations.Column;
import com.revature.orm.annotations.Table;
import com.revature.orm.annotations.PK;
import com.revature.orm.annotations.FK;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Class that represents an annotated class
 * @param <T> the generic object class type
 */
public class Metamodel<T> {

    private Class<T> clazz;
    private PrimaryKey primaryKeyField;
    private List<ColumnField> columnFields;
    private List<ForeignKeyField> foreignKeyFields;

    /**
     * creates a metamodel of a class and throw an exception if the table is not annotated correctly
     * @param clazz the class of the new meta model
     * @param <T> generic class type
     * @return returns the new meta model created
     */
    public static <T> Metamodel<T> of(Class<T> clazz) {
        if (clazz.getAnnotation(Table.class) == null) {
            throw new IllegalStateException("Cannot create Metamodel object! Provided class, " + clazz.getName() + "is not annotated with @Entity");
        }
        return new Metamodel<>(clazz);
    }

    /**
     * Constructor for meta model
     * @param clazz the class to model
     */
    public Metamodel(Class<T> clazz) {
        this.clazz = clazz;
        this.columnFields = new LinkedList<>();
        this.foreignKeyFields = new LinkedList<>();
    }

    public String getClassName() {
        return clazz.getName();
    }

    public Class<T> getModel() { return clazz; }

    public String getSimpleClassName() {
        return clazz.getSimpleName();
    }

    /**
     * gets the primary key field of a class
     * @return the pk field that corresponds to the primary key
     */
    public PrimaryKey getPrimaryKey() {

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            PK primaryKey = field.getAnnotation(PK.class);
            if (primaryKey != null) {
                return new PrimaryKey(field);
            }
        }
        throw new RuntimeException("Did not find a field annotated with @Id in: " + clazz.getName());
    }

    /**
     * Gets the fields annotated with a Column annotation
     * @return the List of ColumnFields for a class
     */
    public List<ColumnField> getColumns() {

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                columnFields.add(new ColumnField(field));
            }
        }

        if (columnFields.isEmpty()) {
            throw new RuntimeException("No columns found in: " + clazz.getName());
        }

        return columnFields;
    }

    /**
     * gets the foreign key field of a class
     * @return the fk field that corresponds to the foreign key
     */
    public List<ForeignKeyField> getForeignKeys() {

        List<ForeignKeyField> foreignKeyFields = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            FK column = field.getAnnotation(FK.class);
            if (column != null) {
                foreignKeyFields.add(new ForeignKeyField(field));
            }
        }

        return foreignKeyFields;

    }

    /**
     * gets the field name of a given column
     * @param columnName the name of the column
     * @return the field name of the column else null
     */
    public String findFieldNameOfColumn(String columnName){
        for(ColumnField c : this.getColumns()){
            if(c.getColumnName().equals(columnName)){
                return c.getName();
            }
        }
        if(getPrimaryKey().getColumnName().equals(columnName)){
            return getPrimaryKey().getName();
        }
        for(ForeignKeyField f : getForeignKeys()){
            if(f.getColumnName().equals(columnName)){
                return f.getName();
            }
        }
        return null;
    }

    /**
     * gets the class type of a given column
     * @param columnName the name of the column
     * @return the class type of column else returns null
     */
    public Class<?> findClassOfColumn(String columnName){
        for(ColumnField c : this.getColumns()){
            if(c.getColumnName().equals(columnName)){
                return c.getType();
            }
        }
        if(getPrimaryKey().getColumnName().equals(columnName)){
            return getPrimaryKey().getType();
        }
        for(ForeignKeyField f : getForeignKeys()){
            if(f.getColumnName().equals(columnName)){
                return f.getType();
            }
        }
        return null;
    }

}
