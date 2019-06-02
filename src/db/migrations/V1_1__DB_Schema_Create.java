package db.migrations;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

public class V1_1__DB_Schema_Create extends BaseJavaMigration {

	public void migrate(Context context) throws Exception {
		
		String createBarsTable =
				"CREATE TABLE IF NOT EXISTS bars (" +
						"id INTEGER NOT NULL AUTO_INCREMENT," +
						"name VARCHAR (50) NOT NULL," +
						"address VARCHAR (50) NOT NULL," +
						"city VARCHAR (50) NOT NULL," +
						"country VARCHAR (50) NOT NULL," +
						"PRIMARY KEY (id)" +
						");";

						MigrationTools.executeQuery(context, createBarsTable);

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
		
						MigrationTools.executeQuery(context, createDrinksTable);

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

						MigrationTools.executeQuery(context, createOrdersTable);

		String createUsersTable =
				"CREATE TABLE IF NOT EXISTS users (" +
						"id INTEGER NOT NULL AUTO_INCREMENT," +
						"userName VARCHAR (50) NOT NULL," +
						"passwordHash VARCHAR(100) NOT NULL," +
						"bar_id INTEGER," +
						"PRIMARY KEY (id)," +
						"FOREIGN KEY (bar_id) REFERENCES bars(id)" +
						");";

						MigrationTools.executeQuery(context, createUsersTable);
	}
}
