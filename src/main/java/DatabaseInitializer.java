// Note: You will need to update the database path on line 19 to match your local machine and setup.
// Also, make sure to add the Derby JAR files to your project’s build path to avoid driver issues.
// To do this:
// 1. Right-click your project and select Properties.
// 2. Go to Java Build Path > Libraries tab.
// 3. Click “Add External JARs…” or “Add External JARs to Modulepath”.
// 4. Add the Derby JAR files you downloaded (at minimum derby.jar).
//
// Important: I experienced issues adding Derby, so to be safe, I included all these JARs:
// derby.jar, derbyClient.jar, derbyNet.jar, and derbyOptionalTools.jar.
// I’m not sure if all are necessary, but since this setup works for me, I’m keeping it as is.

//You can add to your table in schema.sql file

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    private static final String dbURLembedded = "jdbc:derby:C:/Users/skkae/MyDB;create=true";
    private static Connection conn = null;

    public static void main(String[] args) {
        createConnection();
      //  dropTablesIfExist();   //can delete or use
        createTables();
        shutdown();
        System.out.println("Database initialized with tables only.");
    }

    private static void createConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            conn = DriverManager.getConnection(dbURLembedded);
            System.out.println("Connected to database.");
        } catch (Exception except) {
            except.printStackTrace();
        }
    }
    
 /*   private static void dropTablesIfExist() {
        try (Statement stmt = conn.createStatement()) {
            try {
                stmt.executeUpdate("DROP TABLE APP.MONTHLY_EXPENSES");
                System.out.println("Dropped table: monthly_expenses");
            } catch (SQLException e) {
                System.out.println("Could not drop monthly_expenses: " + e.getMessage());
            }
            try {
                stmt.executeUpdate("DROP TABLE APP.MONTHLY_INCOME");
                System.out.println("Dropped table: monthly_income");
            } catch (SQLException e) {
                System.out.println("Could not drop monthly_income: " + e.getMessage());
            }
            try {
                stmt.executeUpdate("DROP TABLE APP.USERS");
                System.out.println("Dropped table: users");
            } catch (SQLException e) {
                System.out.println("Could not drop users: " + e.getMessage());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
*/

    private static void createTables() {
    	  String createUsersTable = 
    		        "CREATE TABLE users (" +
    		        " user_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
    		        " username VARCHAR(100) UNIQUE NOT NULL, " +
    		        " password VARCHAR(255) NOT NULL, " +
    		        " balance DECIMAL(12,2) DEFAULT 0.00, " +
    		        " monthly_savings DECIMAL(12,2) DEFAULT 0.00" +
    		        ")";

        String createMonthlyIncomeTable = 
            "CREATE TABLE monthly_income (" +
            " income_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY" +
            ")";

        String createMonthlyExpensesTable = 
            "CREATE TABLE monthly_expenses (" +
            " expense_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY" +
            ")";

        try (Statement stmt = conn.createStatement()) {
            try {
                stmt.executeUpdate(createUsersTable);
                System.out.println("Created table: users");
            } catch (SQLException e) {
                System.out.println("Table 'users' already exists or error: " + e.getMessage());
            }

            try {
                stmt.executeUpdate(createMonthlyIncomeTable);
                System.out.println("Created table: monthly_income");
            } catch (SQLException e) {
                System.out.println("Table 'monthly_income' already exists or error: " + e.getMessage());
            }

            try {
                stmt.executeUpdate(createMonthlyExpensesTable);
                System.out.println("Created table: monthly_expenses");
            } catch (SQLException e) {
                System.out.println("Table 'monthly_expenses' already exists or error: " + e.getMessage());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static void shutdown() {
        try {
            if (conn != null) {
                DriverManager.getConnection(dbURLembedded + ";shutdown=true");
                conn.close();
                System.out.println("Database shutdown.");
            }
        } catch (SQLException sqlExcept) {
            // This exception is expected on successful shutdown, so can be ignored
        }
    }
}