package db.migrations;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

public class V1_4__Log_table extends BaseJavaMigration {
	public void migrate(Context context) throws Exception {

        String createLogsTable =
				"CREATE TABLE IF NOT EXISTS logs (" +
						"id INTEGER NOT NULL AUTO_INCREMENT," +
						"entry_date TIMESTAMP NOT NULL," +
						"logger varchar(100) NOT NULL," +
						"action varchar(100) NOT NULL," +
						"message TEXT," +
						"PRIMARY KEY (id)" +
            ");";
                        
			MigrationTools.executeQuery(context, createLogsTable);
	}
}
