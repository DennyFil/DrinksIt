
USE DrinksItDB;
SELECT o.ts_create, o.ts_update, o.order_id, o.drink_id, d.name, d.size, o.quantity, o.status, d.bar_id FROM orders o, drinks d, users u WHERE u.username = "denis" AND o.drink_id = d.drink_id AND d.bar_id = u.bar_id;


INSERT into orders (drink_id, quantity, status) values (1, 1, 1);
INSERT into orders (drink_id, quantity, status) values (3, 1, 0);
INSERT into orders (drink_id, quantity, status) values (4, 1, 2);


SELECT o.ts_create, o.ts_update, o.order_id, o.drink_id, d.name, d.size, o.quantity, o.status FROM orders o, drinks d, users u

USE DrinksItDB;
SELECT * FROM orders

USE DrinksItDB;
SELECT o.ts, o.order_id, o.drink_id, d.name, d.size, o.quantity, d.price, o.status
	FROM orders o, drinks d
	WHERE o.drink_id = d.drink_id