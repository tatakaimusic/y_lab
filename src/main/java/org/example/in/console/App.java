package org.example.in.console;


import liquibase.exception.LiquibaseException;
import java.io.IOException;
import java.sql.SQLException;


public class App {
    public static void main(String[] args) throws IOException, LiquibaseException, SQLException {
        ConsoleInterface consoleInterface = new ConsoleInterface();
        consoleInterface.run();
    }
}