package org.example.in.console;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.example.sql.SqlHelper;

import java.sql.Connection;

/**
 * Класс для работы с миграцией.
 */
public class Migration {

    private final String url;
    private final String username;
    private final String password;
    private final String prodSchema;
    private final String migrationSchema;

    public Migration(
            String url,
            String username,
            String password,
            String prodSchema,
            String migrationSchema
    ) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.prodSchema = prodSchema;
        this.migrationSchema = migrationSchema;
    }

    /**
     * Создание схем для продакшена и миграции.
     * Загрузка changesets.
     * @throws LiquibaseException
     */
    public void migrate() throws LiquibaseException {
        SqlHelper.createSchema(
                url,
                username,
                password,
                prodSchema, migrationSchema
        );

        Connection connection = SqlHelper.getConnection(
                url + "?currentSchema=" + prodSchema,
                username,
                password);

        Database database = DatabaseFactory
                .getInstance()
                .findCorrectDatabaseImplementation(
                        new JdbcConnection(connection)
                );
        database.setLiquibaseSchemaName(migrationSchema);

        Liquibase liquibase = new Liquibase(
                "db/changelog/db.changelog-master.yaml",
                new ClassLoaderResourceAccessor(),
                database
        );
        liquibase.update();
    }

}


