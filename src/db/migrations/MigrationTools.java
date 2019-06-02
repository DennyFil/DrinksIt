package db.migrations;

import org.flywaydb.core.api.migration.Context;
import java.sql.PreparedStatement;

public class MigrationTools {

    public static void executeQuery(Context context, String query) throws Exception {

		try (PreparedStatement statement = 
                 context
                     .getConnection()
                     .prepareStatement(query)) {
            statement.execute();
        }
	}
}