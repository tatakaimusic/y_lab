package org.example.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Конфиг для работы с базой данных.
 */
public class DatabaseConfig {

    /**
     * Instance кофинга.
     */
    private static final DatabaseConfig INSTANCE = new DatabaseConfig();

    /**
     * Путь к файлу с пропертис.
     */
    private static final String PROPS = ".env";

    private final Properties props = new Properties();

    /**
     * Загрузка пропертис.
     */
    private DatabaseConfig() {
        try (InputStream is = new FileInputStream(PROPS)) {
            props.load(is);
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS);
        }
    }

    /**
     * Получение Instance.
     * @return
     */
    public static DatabaseConfig getInstance() {
        return INSTANCE;
    }

    /**
     * Получение url базы данных.
     * @return
     */
    public String getUrl(){
        return props.getProperty("POSTGRES_URL");
    }

    /**
     * Получение username базы данных.
     * @return
     */
    public String getUser(){
        return props.getProperty("POSTGRES_USER");
    }

    /**
     * Получение пароля базы данных.
     * @return
     */
    public String getPassword(){
        return props.getProperty("POSTGRES_PASSWORD");
    }

    /**
     * Получение названия базы данных.
     * @return
     */
    public String getDatabaseName(){
        return props.getProperty("POSTGRES_DB");
    }

    /**
     * Получение названия схема для продакшена.
     * @return
     */
    public String getProductionSchema(){
        return props.getProperty("PROD_SCHEMA");
    }

    /**
     * Получение названия схема для миграций.
     * @return
     */
    public String getMigrationSchema(){
        return props.getProperty("MIGRATION_SCHEMA");
    }

}
