# justORM_p1

## Project Description
justORM is a simple ORM created by Tuan Mai. This ORM is able to implement simple CRUD methods such as:
Inserting, Select All, Updating, and Deleting. This ORM is used with PostgreSQL databases.

## Technologies Used
* Java - version 8.0
* Apache Maven - version 3.6.3
* PostGreSQL - version 12.4
* Git SCM (on GitHub) - version 2.30.1

##Features

List of features ready and TODOs for future development
* A public API that can be added as a dependency in other projects
* Programmatic configuration of entities
* Programmatic persistence of entities (basic CRUD support)
* Lightweight session creation

to-do list:
* Connection Pooling
* Implemented Foregin Key references


## Getting Started

### Configuration (Maven Dependency)

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

### Configuration (application.properties)

Must include insde application.properties:
  * url= jdbc:postgresql://host:port/database
  * username = username 
  * password = password 
  
### Configuration (Annotations)
  
  * @Column - fields that represent a column inside a table. @Column element 
     must have "columnName" the same as inside the table.
  * @PK - fields that represent the primary key inside a table. @PK element 
     must have "columnName" the same as inside the table.
  * @FK - fields that represent the foreign key inside a table. @FK element 
     must have "columnName" the same as inside the table.
  * @Table - class that represents the table name. @Table element "tableName" 
     must be the same as talbe name in database.
     
### Configuration (Session)

        SessionFactory sessionFactory = new Configuration()
                                            .addAnnotatedClass(User.class)
                                            .buildSessionFactory();

        Session session = sessionFactory.openSession();

## Usage

The justORM framework relies on intergrating annotations into your project. The POJOs must be annotated with correct
annotation such as @Column, @PK, @Table, @FK. The user must provide a application.properties in the path: 
"src/main/resources/application.properties". The user must include three classes(Session, SessionFactory, Configuration)
inside of their main method to utilized the different methods inside this ORM. 

## License
