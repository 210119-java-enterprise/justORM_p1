package com.revature.orm;

import com.revature.orm.models.User;
import com.revature.orm.util.*;
import com.revature.orm.util.session.Session;
import com.revature.orm.util.session.SessionFactory;

import java.util.List;

public class OrmDriver {

    public static void main(String[] args) {

//        Configuration cfg = new Configuration();
//        cfg.addAnnotatedClass(User.class);
//
//        for (Metamodel<?> metamodel : cfg.getMetamodels()) {
//
//            System.out.printf("Printing metamodel for class: %s\n", metamodel.getClassName());
//            PrimaryKey idField = metamodel.getPrimaryKey();
//            List<ColumnField> columnFields = metamodel.getColumns();
//            List<ForeignKeyField> foreignKeyFields = metamodel.getForeignKeys();
//
//            System.out.printf("\tFound a primary key field named %s of type %s, which maps to the column with the name: %s\n", idField.getName(), idField.getType(), idField.getColumnName());
//
//            for (ColumnField columnField : columnFields) {
//                System.out.printf("\tFound a column field named: %s of type %s, which maps to the column with the name: %s\n", columnField.getName(), columnField.getType(), columnField.getColumnName());
//            }
//
//            for (ForeignKeyField foreignKeyField : foreignKeyFields) {
//                System.out.printf("\tFound a foreign key field named %s of type %s, which maps to the column with the name: %s\n", foreignKeyField.getName(), foreignKeyField.getType(), foreignKeyField.getColumnName());
//            }
//
//            System.out.println();
//        }


//        User user = new User("id1", "tuan", "mai", "email");
//        User user1 = new User("id2", "mike", "loop", "kiching");
//        User user2 = new User("id3", "mike", "loofe", "noo");
//        User updatedUser = new User("id1","updated", "updated", "updated");
//
        SessionFactory sessionFactory = new Configuration()
                                            .addAnnotatedClass(User.class)
                                            .buildSessionFactory();

        Session session = sessionFactory.openSession();


//        int added = session.save(user);
//        System.out.println(added);
        //int added = session.save(user1);
//        int added = session.save(user2);
//
//        System.out.println(added);

//        int updated = session.update(updatedUser, user);
//
//        System.out.println(updated);


//s


//        int deleted = session.delete(user1);
//
//        System.out.println(deleted);

    }

}
