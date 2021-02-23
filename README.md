# justORM_p1

justORM is a simple ORM created by Tuan Mai. This ORM is able to implement simple CRUD methods such as:
Inserting, Select All, Updatingm and Deleting. This ORM is used with PostgreSQL databases.

## Rundown

The justORM framework relies on intergrating annotations into your project. The POJOs must be annotated with correct
annotation such as @Column, @PK, @Table, @FK. The user must provide a application.properties in the path: 
"src/main/resources/application.properties". The user must include three classes(Session, SessionFactory, Configuration)
inside of their main method to utilized the different methods inside this ORM. 

## Configuration (Maven Dependency)

Include this dependency in your pom.xml file:

```xml

    <dependencies>
        <dependency>
            <groupId>org.example</groupId>
            <artifactId>orm</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
    </dependencies>

```

## Configuration (application.properties)

Must include insde application.properties:
  url= jdbc:postgresql://host:port/database
  username = username 
  password = password 
  
## Configuration (Annotations)
  
  1. @Column - fields that represent a column inside a table. @Column element 
     must have "columnName" the same as inside the table
  2. @PK - fields that represent the primary key inside a table. @Column element 
     must have "columnName" the same as inside the table
  3. @FK - fields that represent the foreign key inside a table. @Column element 
     must have "columnName" the same as inside the table
  4. @Table
