package db.migrations;

import org.flywaydb.core.api.migration.spring.SpringJdbcMigration;
import org.springframework.jdbc.core.JdbcTemplate;

public class V1_4__Log_table implements SpringJdbcMigration {
	public void migrate(JdbcTemplate jdbcTemplate) throws Exception {

        String createLogsTable =
				"CREATE TABLE IF NOT EXISTS logs (" +
						"id INTEGER NOT NULL AUTO_INCREMENT," +
						"entry_date TIMESTAMP NOT NULL," +
						"logger_id INTEGER NOT NULL," +
						"level TEXT NOT NULL," +
						"message TEXT NOT NULL," +
						"exception TEXT NOT NULL," +
						"PRIMARY KEY (id)," +
						"FOREIGN KEY (logger_id) REFERENCES users(id)" +
            ");";
                        
		jdbcTemplate.execute(createLogsTable);
	}
}
