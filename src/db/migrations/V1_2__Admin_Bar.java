package db.migrations;

import org.flywaydb.core.api.migration.spring.SpringJdbcMigration;
import org.springframework.jdbc.core.JdbcTemplate;

public class V1_2__Admin_Bar implements SpringJdbcMigration {
	public void migrate(JdbcTemplate jdbcTemplate) throws Exception {

		// Must run "SET GLOBAL sql_mode='NO_AUTO_VALUE_ON_ZERO'" manually when installation DB server
    	String createBar = "INSERT INTO bars (id, name, address, city, country) VALUES ('0', 'ALL', 'ALL', 'ALL', 'ALL')";
        jdbcTemplate.execute(createBar);
    }
}
