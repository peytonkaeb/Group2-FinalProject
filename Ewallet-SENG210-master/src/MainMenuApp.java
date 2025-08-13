import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class MainMenuApp {

		//initial connection, won't work if your already connected
    private static final String DB_URL = "jdbc:derby:C:/Users/skkae/MyDB;create=true";
    private static Connection conn;

    	//main method that calls all the methods in order, it also has a try and catch statement for exceptions
    //connects to the database/if the connection fails, it prints the error and stops 
    public static void main(String[] args) {
        try {
            connectDatabase();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        //swingutilites runs the main menu method 
        SwingUtilities.invokeLater(MainMenuApp::showMainMenu);
    }
    
    
    	//connectdatabase() establishes a connection to the derby database
    private static void connectDatabase() throws Exception {
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
        conn = DriverManager.getConnection(DB_URL);
        System.out.println("Connected to existing database.");
    }

    
    	//showmainmenu() displays the main menu window
    	//in this code, clicking login button closes this window and opens showloginscreen()
    	// clicking the create user button closes this window and opens the showcreateuserscreen()
    private static void showMainMenu() {
        JFrame frame = new JFrame("E-Wallet - Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new FlowLayout());

        JButton loginButton = new JButton("Login");
        JButton createUserButton = new JButton("Create User");

        loginButton.addActionListener(e -> {
            frame.dispose();
            showLoginScreen();
        });

        createUserButton.addActionListener(e -> {
            frame.dispose();
            showCreateUserScreen();
        });

        frame.add(loginButton);
        frame.add(createUserButton);
        frame.setVisible(true);
    }

    
    	//displays textboxes with username and password fields
    	//when login button is clicked:
    	//if empty it shows a red warning
    	//otherwise it calls validateuser(username, password)
    	
    private static void showLoginScreen() {
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 250);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JTextField usernameInput = new JTextField();
        usernameInput.setMaximumSize(new Dimension(200, 25));

        JPasswordField passwordInput = new JPasswordField();
        passwordInput.setMaximumSize(new Dimension(200, 25));

        JButton loginButton = new JButton("Login");
        JLabel feedbackLabel = new JLabel(" ");
        feedbackLabel.setForeground(Color.RED);

        loginButton.addActionListener(e -> {
            String username = usernameInput.getText().trim();
            String password = new String(passwordInput.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                feedbackLabel.setText("Please enter both username and password.");
                return;
            }

            if (validateUser(username, password)) {
                feedbackLabel.setForeground(Color.GREEN);
                feedbackLabel.setText("Login successful!");
                // You can add further actions here on successful login
            } else {
                feedbackLabel.setForeground(Color.RED);
                feedbackLabel.setText("Invalid username or password.");
            }
        });

        panel.add(new JLabel("Username:"));
        panel.add(usernameInput);
        panel.add(new JLabel("Password:"));
        panel.add(passwordInput);
        panel.add(Box.createVerticalStrut(10));
        panel.add(loginButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(feedbackLabel);

        frame.add(panel);
        frame.setVisible(true);
    }

    
    	//allows someone to make a new account 
    	//displays new username and new password field 
    	//otherwise calls createuser(username,password)
    private static void showCreateUserScreen() {
        JFrame frame = new JFrame("Create User");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 250);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JTextField usernameInput = new JTextField();
        usernameInput.setMaximumSize(new Dimension(200, 25));

        JPasswordField passwordInput = new JPasswordField();
        passwordInput.setMaximumSize(new Dimension(200, 25));

        JButton createButton = new JButton("Create Account");
        JLabel feedbackLabel = new JLabel(" ");
        feedbackLabel.setForeground(Color.BLUE);

        createButton.addActionListener(e -> {
            String username = usernameInput.getText().trim();
            String password = new String(passwordInput.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                feedbackLabel.setForeground(Color.RED);
                feedbackLabel.setText("Username and password cannot be empty.");
                return;
            }

            if (userExists(username)) {
                feedbackLabel.setForeground(Color.RED);
                feedbackLabel.setText("Username already exists. Try another.");
                return;
            }

            if (createUser(username, password)) {
                feedbackLabel.setForeground(Color.BLUE);
                feedbackLabel.setText("Account created successfully!");
                Timer timer = new Timer(2000, ev -> {
                    frame.dispose();
                    showMainMenu();
                });
                timer.setRepeats(false);
                timer.start();
            } else {
                feedbackLabel.setForeground(Color.RED);
                feedbackLabel.setText("Error occurred while creating user.");
            }
        });

        panel.add(new JLabel("New Username:"));
        panel.add(usernameInput);
        panel.add(new JLabel("New Password:"));
        panel.add(passwordInput);
        panel.add(Box.createVerticalStrut(10));
        panel.add(createButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(feedbackLabel);

        frame.add(panel);
        frame.setVisible(true);
    }
    //checks if username exists in database (for create user) - avoid duplicates
    private static boolean userExists(String username) {
        String querySQL = "SELECT 1 FROM \"User\" WHERE USERNAME = ?";
        try (PreparedStatement ps = conn.prepareStatement(querySQL)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    //creates and saves user in database
    private static boolean createUser(String username, String password) {
        String insertSQL = "INSERT INTO \"User\" (USERNAME, PWD) VALUES (?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(insertSQL)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error creating user: " + e.getMessage());
            return false;
        }
    }
    //this checks for username and password combo to login
    private static boolean validateUser(String username, String password) {
        String querySQL = "SELECT * FROM \"User\" WHERE USERNAME = ? AND PWD = ?";

        try (PreparedStatement ps = conn.prepareStatement(querySQL)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
