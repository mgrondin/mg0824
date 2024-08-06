package org.ToolRentalPOS.db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;

import java.io.IOException;



//This class serves two purposes.
//It is responsible getting the database connection for other objects like DAOs (Data access objects)
//It also has a main method that useful for setting up and cleaning the database.
//Run main from this class in your IDE to setup the database before running tests.

public class SQLliteConnectionManager  {

    private static final String DB_URL = "jdbc:sqlite:src/main/resources/tools.db";
    private static final String DB_CLEAN_SCRIPT = "src/main/resources/dbClean.sql";

    public static void main(String [] args){
        try {
            executeScript(DB_URL, DB_CLEAN_SCRIPT);
        } catch (SQLException | IOException e){
            System.out.println("There was an exception when trying to run the DB script.");
            e.printStackTrace(); //Not the best logging, but it's a utility...
        }
    }

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(DB_URL);
    }

    private static void executeScript(String dbUrl, String scriptPath) throws SQLException, IOException {

        try (    Connection conn = DriverManager.getConnection(dbUrl);
                 Statement stmt = conn.createStatement();
                ){
            // Read the SQL script from file
            BufferedReader reader = new BufferedReader(new FileReader(scriptPath));
            StringBuilder script = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                if(!line.trim().startsWith("--")) { //Skip lines that are comments Note inline comments not supported
                    script.append(line.trim());
                    // Check if the line ends with a semicolon indicating the end of an SQL statement
                    if (line.trim().endsWith(";")) {
                        stmt.executeUpdate(script.toString());
                        // Clear the script StringBuilder for the next statement
                        script.setLength(0);
                    }
                }
            }
            reader.close();
            System.out.println("Script executed successfully!");
        }
    }

}
