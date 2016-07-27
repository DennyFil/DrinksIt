USE DrinksItDB;
CREATE USER 'drinksitdbuser'@'%' IDENTIFIED BY 'drinksitdbuser';

GRANT ALL PRIVILEGES ON DrinksItDB.* TO 'drinksitdbuser'@'%' IDENTIFIED BY 'drinksitdbuser';