# Bank API Test Project

## Overview
This project is a test project that calls a Bank API. It is built using Java and Spring Boot. The project includes functionalities to interact with the Bank API and perform various operations.

## Features
- **Account Management**: Create, retrieve, and list accounts.
- **Transaction Management**: Deposit, withdraw, and transfer funds between accounts.

## Technologies Used
- **Java**: The primary programming language used for development.
- **Spring Boot**: A framework that simplifies the development of Java applications.
- **Maven**: A build automation tool used for managing project dependencies.
- **JUnit 5**: A testing framework used for writing and running tests.
- **Mockito**: A mocking framework used for unit testing.
- **ModelMapper**: A library used for object mapping.
- **Lombok**: A library that helps to reduce boilerplate code.
- **PostgreSQL**: A relational database used for persisting data.
- **Jakarta Persistence (JPA)**: Used for ORM (Object-Relational Mapping).
- **Jakarta Validation**: Used for validating entities.
- **SLF4J**: A logging framework.
- **OpenTest4J**: A library used for assertion errors in testing.
- **Spring Data JPA**: A part of Spring Data used for data access.
- **Spring Web**: A part of Spring used for building web applications.
- **Spring Boot DevTools**: Provides additional development-time features.
- **Spring Boot Test**: Provides testing support for Spring Boot applications.

## Prerequisites
- **Java 17** or higher
- **Maven 3.6.3** or higher
- **PostgreSQL**: Ensure PostgreSQL is installed and running.

## Design Choices

### 1. Spring Boot
Spring Boot was chosen for its simplicity and ease of integration with other Spring projects. It provides a comprehensive framework for building Java applications quickly and efficiently, with minimal configuration. It also allows for seamless integration with Spring modules like Spring Data JPA and Spring Security.

### 2. ModelMapper
ModelMapper is used for converting between entity and DTO objects, reducing boilerplate code. This library simplifies the mapping process, making it easier to transfer data between different layers of the application without manually writing conversion logic.

### 3. Lombok
Lombok reduces boilerplate code for model classes by automatically generating getters, setters, constructors, and other common methods. This makes the code cleaner, easier to maintain, and improves readability, while reducing the need for repetitive code across the application.

### 4. JUnit 5 and Mockito
JUnit 5 is used for writing unit tests to ensure the correctness of the application. Mockito is employed for mocking dependencies, allowing for isolated testing of the service logic. The combination ensures that the application is thoroughly tested and behaves as expected under various conditions.

## Setup Instructions

### Before Starting 
- Ensure that Java 17 or higher is installed on your system.
- Go to the src/main/resources/sql/Sql.sql file and run the SQL script to create the database and tables. 

### Required PostgreSQL Configurations
```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/bank_app
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### Clone the Repository
```bash
git clone https://github.com/DmytroLysenko1/testTask.git
```
### Switch to the Project Directory
```bash
cd testTask
```
### Build Application
```bash
./mvnw clean install
```
### Run the Application
```bash
./mvnw spring-boot:run
```
### Access the Application in Browser
```bash
http://localhost:8081/swagger-ui/index.html#/
