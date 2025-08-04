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

    private static void createTables() {
        String createUsersTable = 
            "CREATE TABLE users (" +
            " user_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
            " username VARCHAR(100) UNIQUE NOT NULL, " +
            " password_hash VARCHAR(255) NOT NULL, " +
            " email VARCHAR(150) UNIQUE NOT NULL, " +
            " created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
            ")";
        
        String createAccountsTable = 
            "CREATE TABLE accounts (" +
            " account_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
            " user_id INT NOT NULL, " +
            " account_name VARCHAR(100), " +
            " balance DECIMAL(12,2) DEFAULT 0.00, " +
            " FOREIGN KEY (user_id) REFERENCES users(user_id)" +
            ")";
        
        String createTransactionsTable = 
            "CREATE TABLE transactions (" +
            " txn_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
            " account_id INT NOT NULL, " +
            " amount DECIMAL(12,2) NOT NULL, " +
            " txn_type VARCHAR(20) CHECK (txn_type IN ('deposit','withdrawal','transfer')), " +
            " description VARCHAR(255), " +
            " txn_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
            " FOREIGN KEY (account_id) REFERENCES accounts(account_id)" +
            ")";

        try (Statement stmt = conn.createStatement()) {
            // Create users table
            try {
                stmt.executeUpdate(createUsersTable);
                System.out.println("Created table 'users'.");
            } catch (SQLException e) {
                System.out.println("Table 'users' already exists or error: " + e.getMessage());
            }

            // Create accounts table
            try {
                stmt.executeUpdate(createAccountsTable);
                System.out.println("Created table 'accounts'.");
            } catch (SQLException e) {
                System.out.println("Table 'accounts' already exists or error: " + e.getMessage());
            }

            // Create transactions table
            try {
                stmt.executeUpdate(createTransactionsTable);
                System.out.println("Created table 'transactions'.");
            } catch (SQLException e) {
                System.out.println("Table 'transactions' already exists or error: " + e.getMessage());
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