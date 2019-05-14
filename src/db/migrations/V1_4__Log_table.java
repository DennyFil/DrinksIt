package db.migrations;

import org.flywaydb.core.api.migration.spring.SpringJdbcMigration;
import org.springframework.jdbc.core.JdbcTemplate;

public class V1_4__Log_table implements SpringJdbcMigration {
	public void migrate(JdbcTemplate jdbcTemplate) throws Exception {

        String createLogsTable =
				"CREATE TABLE IF NOT EXISTS logs (" +
						"id INTEGER NOT NULL AUTO_INCREMENT," +
						"entry_date TIMESTAMP NOT NULL," +
						"logger varchar(100) NOT NULL," +
						"action varchar(100) NOT NULL," +
						"message TEXT," +
						"PRIMARY KEY (id)" +
            ");";
                        
		jdbcTemplate.execute(createLogsTable);
	}
}
