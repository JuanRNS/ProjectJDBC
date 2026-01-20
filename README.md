# Projeto JDBC - Java & MySQL com Padrão DAO

Este projeto é uma aplicação Java educacional que demonstra o uso de **JDBC (Java Database Connectivity)** com o padrão de projeto **DAO (Data Access Object)**. Ele foi desenvolvido como parte de um curso da Udemy para interagir com um banco de dados MySQL.

## 📋 Sobre o Projeto

O objetivo principal é separar a lógica de acesso a dados da lógica de negócios, permitindo operações de CRUD (Create, Read, Update, Delete) nas entidades `Seller` (Vendedor) e `Department` (Departamento) de forma organizada e manutenível.

## 🚀 Tecnologias Utilizadas

- **Java 17+**
- **JDBC (Java Database Connectivity)**
- **MySQL** (Banco de Dados)
- **Padrão DAO** (Data Access Object)

## 📂 Estrutura do Projeto

A estrutura de pacotes do projeto é organizada da seguinte forma:

- **src/app**: Contém a classe `Main.java` para testar a aplicação no console.
- **src/db**: Contém a classe `DB.java` responsável pela conexão e desconexão com o banco de dados.
- **src/entities**: Contém as classes de modelo (entidades) `Seller` e `Department`.
- **src/model**: Contém as interfaces DAO (`SellerDao`, `DepartmentDao`) e a fábrica `DaoFactory`.
- **src/model/dao/impl**: Contém as implementações JDBC das interfaces DAO (`SellerDaoJDBC`, `DepartmentDaoJDBC`).

## ⚙️ Configuração do Banco de Dados

Para rodar o projeto, você precisa ter o MySQL instalado e criar o banco de dados `coursejdbc`. Execute o script SQL abaixo no seu cliente MySQL (ex: MySQL Workbench):

```sql
CREATE DATABASE coursejdbc;

USE coursejdbc;

CREATE TABLE department (
  Id int(11) NOT NULL AUTO_INCREMENT,
  Name varchar(60) DEFAULT NULL,
  PRIMARY KEY (Id)
);

CREATE TABLE seller (
  Id int(11) NOT NULL AUTO_INCREMENT,
  Name varchar(60) NOT NULL,
  Email varchar(100) NOT NULL,
  BirthDate datetime NOT NULL,
  BaseSalary double NOT NULL,
  DepartmentId int(11) NOT NULL,
  PRIMARY KEY (Id),
  FOREIGN KEY (DepartmentId) REFERENCES department (Id)
);

INSERT INTO department (Name) VALUES 
  ('Computers'), 
  ('Electronics'), 
  ('Fashion'), 
  ('Books');

INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId) VALUES 
  ('Bob Brown','bob@gmail.com','1998-04-21 00:00:00',1000,1),
  ('Maria Green','maria@gmail.com','1979-12-31 00:00:00',3500,2),
  ('Alex Grey','alex@gmail.com','1988-01-15 00:00:00',2200,1),
  ('Martha Red','martha@gmail.com','1993-11-30 00:00:00',3000,4),
  ('Donald Blue','donald@gmail.com','2000-01-09 00:00:00',4000,3),
  ('Alex Pink','bob@gmail.com','1997-03-04 00:00:00',3000,2);
```

## 🔧 Configuração da Aplicação

Certifique-se de que o arquivo `db.properties` na raiz do projeto esteja configurado corretamente com as suas credenciais do MySQL:

```properties
user=root
password=SUA_SENHA_AQUI
dburl=jdbc:mysql://localhost:3306/coursejdbc
useSSL=false
```

> **Nota**: Substitua `SUA_SENHA_AQUI` pela senha do seu usuário root do MySQL.

## ▶️ Como Executar

1. Importe o projeto na sua IDE de preferência (IntelliJ IDEA, Eclipse, NetBeans).
2. Verifique se o driver JDBC do MySQL (MySQL Connector/J) está adicionado às dependências do projeto.
3. Configure o arquivo `db.properties` conforme explicado acima.
4. Execute a classe `src/app/Main.java`.

O programa irá realizar uma série de testes demonstrando inserção, busca, atualização e deleção de vendedores.
