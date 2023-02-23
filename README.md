Record Crate 
============

A Dockerized version of the spring-boot app using tomcat and MySql.

Performs CRUD operations on a music database.

###1. Technologies used
* Java 11
* Spring Boot 1.4.3
* Maven 4
* Docker 2


###2. To import this project into Eclipse IDE
1. ```$ mvn eclipse:eclipse```
2. Import into Eclipse via **existing projects into workspace** option.
3. Use the pom.xml file in the root of the record-crate directory
3. Done.

###3. To build the project and deploy to Docker
1. mvn clean package docker:build
2. docker run -p 3306:3306 --name mysqldb -e MYSQL_ROOT_HOST=% -e MYSQL_ROOT_PASSWORD=<password> -e MYSQL_DATABASE=main_db -e MYSQL_USER=record_user -e MYSQL_PASSWORD=<password> -d mysql:5.6
3. docker run -p 8080:8080 --name record-crate-container --link mysqldb:mysql -d record-crate


