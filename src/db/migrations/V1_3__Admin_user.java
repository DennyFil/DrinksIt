package db.migrations;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import webservice.auxillary.HashComputor;

public class V1_3__Admin_user extends BaseJavaMigration {
    public void migrate(Context context) throws Exception {
    	String password = "z5@MTLEq";
    	String passwordHash = HashComputor.ComputeSHA256(password);
    	String query = "INSERT INTO users (userName, passwordHash, bar_id) VALUES ('DrinksItAdmin', '" + passwordHash + "', 1)";
        
        MigrationTools.executeQuery(context, query);
    }
}
