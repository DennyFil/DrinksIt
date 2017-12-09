package db.migrations;

import org.flywaydb.core.api.migration.spring.SpringJdbcMigration;
import org.springframework.jdbc.core.JdbcTemplate;

public class V1_1__DB_Schema_Create implements SpringJdbcMigration {
	public void migrate(JdbcTemplate jdbcTemplate) throws Exception {
		
		String createBarsTable =
				"CREATE TABLE IF NOT EXISTS bars (" +
						"id INTEGER NOT NULL AUTO_INCREMENT," +
						"name VARCHAR (50) NOT NULL," +
						"address VARCHAR (50) NOT NULL," +
						"city VARCHAR (50) NOT NULL," +
						"country VARCHAR (50) NOT NULL," +
						"PRIMARY KEY (id)" +
						");";
		jdbcTemplate.execute(createBarsTable);		

		String createDrinksTable = 
				"CREATE TABLE IF NOT EXISTS drinks (" +
						"id INTEGER NOT NULL AUTO_INCREMENT," +
						"name VARCHAR(50) NOT NULL," +
						"price DOUBLE NOT NULL," +
						"size DOUBLE NOT NULL," +
						"bar_id INTEGER NOT NULL," +
						"PRIMARY KEY (id)," +
						"FOREIGN KEY (bar_id) REFERENCES bars(id)" +
						");";
		jdbcTemplate.execute(createDrinksTable);

		String createOrdersTable =
				"CREATE TABLE IF NOT EXISTS orders (" +
						"id INTEGER NOT NULL AUTO_INCREMENT," +
						"ts_create TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
						"ts_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
						"drink_id INTEGER NOT NULL," +
						"quantity INTEGER NOT NULL," +
						"status VARCHAR(50) NOT NULL," +
						"PRIMARY KEY (id)," +
						"FOREIGN KEY (drink_id) REFERENCES drinks(id)" +
						");";
		jdbcTemplate.execute(createOrdersTable);

		String createPaymentsTable =
				"CREATE TABLE IF NOT EXISTS payments (" +
						"id INTEGER NOT NULL AUTO_INCREMENT," +
						"ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
						"amount DOUBLE NOT NULL," +
						"order_id INTEGER NOT NULL," +
						"PRIMARY KEY (id)," +
						"FOREIGN KEY (order_id) REFERENCES orders(id)" +
						");";
		jdbcTemplate.execute(createPaymentsTable);

		String createUsersTable =
				"CREATE TABLE IF NOT EXISTS users (" +
						"userName VARCHAR (50) NOT NULL," +
						"passwordHash VARCHAR(100) NOT NULL," +
						"isAdmin BIT NOT NULL," +
						"bar_id INTEGER," +
						"PRIMARY KEY (userName)," +
						"FOREIGN KEY (bar_id) REFERENCES bars(id)" +
						");";
		jdbcTemplate.execute(createUsersTable);
	}
}
