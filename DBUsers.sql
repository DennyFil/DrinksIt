
		
CREATE USER IF NOT EXISTS 'drinksitdbuser'@'%' IDENTIFIED BY 'drinksitdbuser';
GRANT ALL PRIVILEGES ON DrinksItDB.* TO 'drinksitdbuser'@'%' IDENTIFIED BY 'drinksitdbuser';