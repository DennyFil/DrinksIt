package db.migrations;

import org.flywaydb.core.api.migration.spring.SpringJdbcMigration;
import org.springframework.jdbc.core.JdbcTemplate;

import webservice.auxillary.HashComputor;

public class V1_2__Admin_user implements SpringJdbcMigration {
    public void migrate(JdbcTemplate jdbcTemplate) throws Exception {
    	String password = "z5@MTLEq";
    	String passwordHash = HashComputor.ComputeSHA256(password);
    	String query = "INSERT INTO users (userName, passwordHash, isAdmin) VALUES ('DrinksItAdmin', '" + passwordHash + "', true)";
        jdbcTemplate.execute(query);
    }
}
