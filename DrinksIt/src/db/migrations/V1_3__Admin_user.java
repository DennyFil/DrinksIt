package db.migrations;

import org.flywaydb.core.api.migration.spring.SpringJdbcMigration;
import org.springframework.jdbc.core.JdbcTemplate;

import webservice.auxillary.HashComputor;

public class V1_3__Admin_user implements SpringJdbcMigration {
    public void migrate(JdbcTemplate jdbcTemplate) throws Exception {
    	String password = "z5@MTLEq";
    	String passwordHash = HashComputor.ComputeSHA256(password);
    	String query = "INSERT INTO users (userName, passwordHash, bar_id) VALUES ('DrinksItAdmin', '" + passwordHash + "', 0)";
        jdbcTemplate.execute(query);
    }
}
