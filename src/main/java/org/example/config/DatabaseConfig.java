package org.example.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {

    private static final DatabaseConfig INSTANCE = new DatabaseConfig();

    private static final String PROPS = ".env";

    private final Properties props = new Properties();

    private DatabaseConfig() {
        try (InputStream is = new FileInputStream(PROPS)) {
            props.load(is);
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS);
        }
    }

    public static DatabaseConfig getInstance() {
        return INSTANCE;
    }

    public String getUrl(){
        return props.getProperty("POSTGRES_URL");
    }

    public String getUser(){
        return props.getProperty("POSTGRES_USER");
    }

    public String getPassword(){
        return props.getProperty("POSTGRES_PASSWORD");
    }

    public String getDatabaseName(){
        return props.getProperty("POSTGRES_DB");
    }

    public String getProductionSchema(){
        return props.getProperty("PROD_SCHEMA");
    }

    public String getMigrationSchema(){
        return props.getProperty("MIGRATION_SCHEMA");
    }

}
