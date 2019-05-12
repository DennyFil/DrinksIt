package db.migrations;

import org.flywaydb.core.api.migration.spring.SpringJdbcMigration;
import org.springframework.jdbc.core.JdbcTemplate;

public class V1_4__Log_table implements SpringJdbcMigration {
	public void migrate(JdbcTemplate jdbcTemplate) throws Exception {

        String createLogsTable =
				"CREATE TABLE IF NOT EXISTS logs (" +
						"id varchar(100) primary key," +
						"entry_date TIMESTAMP NOT NULL," +
						"logger_id INTEGER NOT NULL," +
						"action varchar(100)," +
						"message TEXT" +
						"FOREIGN KEY (logger_id) REFERENCES users(id)" +
            ");";
                        
		jdbcTemplate.execute(createLogsTable);
	}
}
