package de.iws.utils.db;

import de.iws.jdbc.ResourceManager;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;

import javax.sql.DataSource;

/**
 * Take care of database migration
 *
 * Uses flyway to upgrade/migrate database schemas
 */
public class Migration {
    private Migration() {
    }

    public static void doUpgrade() throws FlywayException {
        upgrade(ResourceManager.getDataSource());
    }

    /**
     * Upgrade the iws schema
     *
     * @param dataSource which datasource to use
     */
    private static void upgrade(DataSource dataSource) throws FlywayException {
        Flyway flyway = Flyway.configure().locations("migration").schemas("iws").dataSource(dataSource).load();
        flyway.migrate();
    }
}
