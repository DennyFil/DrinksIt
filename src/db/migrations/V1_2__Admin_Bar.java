package db.migrations;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

public class V1_2__Admin_Bar extends BaseJavaMigration {
	public void migrate(Context context) throws Exception {

		// Must run "SET GLOBAL sql_mode='NO_AUTO_VALUE_ON_ZERO'" manually when installation DB server
    	String createBar = "INSERT INTO bars (name, address, city, country) VALUES ('ALL', 'ALL', 'ALL', 'ALL')";
		
		MigrationTools.executeQuery(context, createBar);
    }
}
