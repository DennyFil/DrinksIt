DROP DATABASE IF EXISTS DrinksItDB;
CREATE DATABASE DrinksItDB;
 
USE DrinksItDB;
 CREATE TABLE bars (
  bar_id INTEGER NOT NULL AUTO_INCREMENT,
  name VARCHAR (50) NOT NULL,
  address VARCHAR (50) NOT NULL,
  city VARCHAR (50) NOT NULL,
  country VARCHAR (50) NOT NULL,
  PRIMARY KEY (bar_id)
 );

USE DrinksItDB;
 CREATE TABLE drinks (
  drink_id INTEGER NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  price DOUBLE NOT NULL,
  size DOUBLE NOT NULL,
  bar_id INTEGER NOT NULL,
  PRIMARY KEY (drink_id),
  FOREIGN KEY (bar_id) REFERENCES bars(bar_id)
 );
 
USE DrinksItDB;
CREATE TABLE orders (
  order_id INTEGER NOT NULL AUTO_INCREMENT,
  ts_create VARCHAR(50) NOT NULL,
  ts_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  drink_id INTEGER NOT NULL,
  quantity INTEGER NOT NULL,
  status VARCHAR(50) NOT NULL,
  PRIMARY KEY (order_id),
  FOREIGN KEY (drink_id) REFERENCES drinks(drink_id)
 );
  
 USE DrinksItDB;
 CREATE TABLE payments (
  payment_id INTEGER NOT NULL AUTO_INCREMENT,
  ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  amount DOUBLE NOT NULL,
  order_id INTEGER NOT NULL,
  PRIMARY KEY (payment_id),
  FOREIGN KEY (order_id) REFERENCES orders(order_id)
 );

USE DrinksItDB;
CREATE TABLE users (
 	userName VARCHAR (50) NOT NULL,
 	passwordHash VARCHAR(100) NOT NULL,
 	bar_id INTEGER NOT NULL,
 	PRIMARY KEY (userName),
	FOREIGN KEY (bar_id) REFERENCES bars(bar_id)
 );
 
USE DrinksItDB;
CREATE TABLE admins (
 	userName VARCHAR (50) NOT NULL,
 	passwordHash VARCHAR(100) NOT NULL,
 	PRIMARY KEY (userName)
 );
 
USE DrinksItDB;
SHOW TABLES;