

//import java.awt.BorderLayout;
//import java.awt.Dimension;
//import java.awt.FlowLayout;
//import java.awt.GridBagConstraints;
//import java.awt.GridBagLayout;
//import java.awt.Insets;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.ArrayList;
//import java.util.Timer;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.Statement;
//
//import javax.swing.text.DocumentFilter;
//import javax.swing.text.AbstractDocument;
//import javax.swing.text.AttributeSet;
//import javax.swing.text.BadLocationException;
//import javax.swing.BoxLayout;
//import javax.swing.JButton;
//import javax.swing.JComboBox;
//import javax.swing.JFileChooser;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.JPasswordField;
//import javax.swing.JTextArea;
//import javax.swing.JTextField;
//import javax.swing.filechooser.FileNameExtensionFilter;
//
//
//public class EWalletApp {
//
//    // this is the app class, has the GUI and create one object of your expense calculator class.
//    // The expense calculator class is the implementation of the Expenser interface 
//    private ArrayList<User> allData;
//    private static ExpenseCalculator expenseCalculator = new ExpenseCalculator();
//    private static User currUser = new User();
//    private static String dbURLembedded = "jdbc:derby:C:/Users/gorby/MyDB;create=true";
//    private static String dbURL = "jdbc:derby://localhost:1527/myDB;create=true";
//    
//    private static String expenseTableName = "EXPENSE";
//    
//    
//    private static Connection conn = null;
//    private static Statement stmt = null;
//
//    public static void main(String[] args) {
//        ExpenseCalculator expenseCalculator = new ExpenseCalculator(); // had to create here to get around static BS
//        InitalizeLoginScreen(expenseCalculator);
//    }
//	
//
//	private static void InitalizeLoginScreen(ExpenseCalculator expenseCalculator) {
//
//		// Inital JFrame stuff
//		JFrame jframe = new JFrame("E-Wallet App - Login");
//		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		jframe.setSize(400, 250);
//		jframe.setLocationRelativeTo(null); // center screen
//
//		// Components
//		JLabel userLabel = new JLabel("Username:");
//		JTextField usernameInput = new JTextField();
//		usernameInput.setPreferredSize(new Dimension(200, 25));
//
//		JLabel passLabel = new JLabel("Password:");
//		JPasswordField passwordInput = new JPasswordField();
//		passwordInput.setPreferredSize(new Dimension(200, 25));
//
//		JButton confirmLoginButton = new JButton("Login");
//		JLabel feedbackLabel = new JLabel(" ");
//		feedbackLabel.setForeground(java.awt.Color.RED);
//
//		// Panel layout
//		JPanel panel = new JPanel();
//		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//		panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 40, 20, 40));
//
//		panel.add(userLabel);
//		panel.add(usernameInput);
//		panel.add(passLabel);
//		panel.add(passwordInput);
//		panel.add(javax.swing.Box.createVerticalStrut(10));
//		panel.add(confirmLoginButton);
//		panel.add(javax.swing.Box.createVerticalStrut(10));
//		panel.add(feedbackLabel);
//
//		// Action listener
//		confirmLoginButton.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				String username = usernameInput.getText().trim();
//				String password = new String(passwordInput.getPassword()).trim();
//
//
//               if(usernameInput.getText() == null || passwordInput.getText() == null || usernameInput.getText().isEmpty() || passwordInput.getText().isEmpty())
//               {
//            	   feedbackLabel.setText("Please enter both username and password.");
//               }
//               else
//               {
//            	   User user = new User(usernameInput.getText(), passwordInput.getText());
//            	   expenseCalculator.userAtHand = user;
//            	   InitalizeReportScreen(expenseCalculator);
//            	   jframe.dispose(); //destroy self now that new JFrame is here
//               }
//            }
//				});
//		;
//		
//		jframe.add(panel);
//        jframe.setVisible(true);
//
//	}
//											//once again included to work around static class
//	private static void InitalizeReportScreen(ExpenseCalculator expenseCalculator)
//	{
//		//inital Jframe stuff
//		JFrame jframe = new JFrame();
//		jframe.setTitle("E-Wallet App");
//		jframe.setDefaultCloseOperation(jframe.EXIT_ON_CLOSE);
//		jframe.setSize(800, 300);
//		
//		//Creating GUI stuff
//		JLabel incomeLabel = new JLabel("Add Income (per month)"); 
//		JLabel expenseLabel = new JLabel("Add Expense"); 
//				
//		JTextField incomeInput = new JTextField();
//		JTextField expenseInput = new JTextField();
//		
//		
//		//little messages that show up under the input areas to show it went through
//		JLabel incomeConfirmation = new JLabel("");
//		JLabel expenseConfirmation = new JLabel("");
//		
//		//Admittedly code taken from chatGPT, to prevent none numbers from being inputed
//		DocumentFilter numberFilter = new NumericFilter();
//		((AbstractDocument) incomeInput.getDocument()).setDocumentFilter(numberFilter);
//		((AbstractDocument) expenseInput.getDocument()).setDocumentFilter(numberFilter);
//		
//		
//		JButton confirmIncomeButton = new JButton("Add");
//		JButton loadIncomeFromFileButton = new JButton("Import Income");
//		loadIncomeFromFileButton.addActionListener(event -> importReport());   //TEMPORARY -> Replace with intended functionality when ready
//
//		JButton confirmExpenseButton = new JButton("Add");
//		JButton loadExpenseFromFileButton = new JButton("Import Expense");
//		JButton saveExpenseButton = new JButton("Save Expense");
//		loadExpenseFromFileButton.addActionListener(event -> importReport());   //TEMPORARY -> Replace with intended functionality when ready
//		saveExpenseButton.addActionListener(event -> saveExpense(expenseInput));
//		
//		// JButton reportButton = new JButton("Print an Expense Report");
//		
//		// Button to open generate report dialog (you can move this wherever)
//		JButton generateReportButton = new JButton("Generate Report");
//		generateReportButton.addActionListener(event -> selectReport());
//				
//		JButton importReportButton = new JButton("Import Report");
//		importReportButton.addActionListener(event -> importReport());
//		
//		
//		//action listeners, I.E button functionality
//		
//		confirmIncomeButton.addActionListener(new ActionListener(){
//
//			@Override
//			public void actionPerformed(ActionEvent e) 
//			{
//				if(incomeInput.getText() == null || incomeInput.getText().isEmpty())
//				{
//					incomeConfirmation.setText("Please insert a number, it can't be blank");
//					
//					//Functionally, resets the text to be blank after 3 seconds
//					javax.swing.Timer time = new javax.swing.Timer(3000, event -> incomeConfirmation.setText(""));
//					time.setRepeats(false);
//					time.start();
//				}
//				else
//				{
//					String newIncomeText = incomeInput.getText();
//					double newIncomeAmount = Double.parseDouble(newIncomeText);
//					//defaults to "Unspecified" because there currently isn't a way TO specify
//					Wage newWage = new Wage("Unspecified", newIncomeAmount);
//					expenseCalculator.userAtHand.addIncome(newWage);
//					
//					incomeConfirmation.setText("New Income Submitted!");
//					
//					//yes Copy+Paste from GPT again, kinda works
//					//would be better if there was 1 timer shared, hence why its a little funky, but eh this works
//					//Functionally, resets the text to be blank after 3 seconds
//					javax.swing.Timer time = new javax.swing.Timer(3000, event -> incomeConfirmation.setText(""));
//					time.setRepeats(false);
//					time.start();
//					
//				}
//				
//			}
//			
//		});
//		
//		confirmExpenseButton.addActionListener(new ActionListener(){
//
//			@Override
//			public void actionPerformed(ActionEvent e) 
//			{
//				if(expenseInput.getText() == null || expenseInput.getText().isEmpty())
//				{
//					//potentially display error message
//					expenseConfirmation.setText("Please insert a number, it can't be blank");
//					
//					//Functionally, resets the text to be blank after 3 seconds
//					javax.swing.Timer time = new javax.swing.Timer(3000, event -> expenseConfirmation.setText(""));
//					time.setRepeats(false);
//					time.start();
//				}
//				else
//				{
//					String newExpenseText = expenseInput.getText();
//					double newExpenseAmount = Double.parseDouble(newExpenseText);
//					//defaults to "Unspecified" because there currently isn't a way TO specify
//					Expense expense = new Expense("Unspecified", newExpenseAmount);
//					expenseCalculator.userAtHand.addExpense(expense);
//					
//					expenseConfirmation.setText("New Expense Submitted!");
//					
//					//Functionally, resets the text to be blank after 3 seconds
//					javax.swing.Timer time = new javax.swing.Timer(3000, event -> expenseConfirmation.setText(""));
//					time.setRepeats(false);
//					time.start();
//				}
//			}
//			
//		});
//		
//		//may need deleting if obsolete
//		/*reportButton.addActionListener(new ActionListener(){
//
//			@Override
//			public void actionPerformed(ActionEvent e) 
//			{
//				//see selectReport
//				selectReport();
//			}
//			
//		});*/
//		
//		// Panels, to organize page
//				JPanel incomePanel = new JPanel();
//				incomePanel.setLayout(new GridBagLayout());
//				
//				JPanel expensePanel = new JPanel();
//				expensePanel.setLayout(new GridBagLayout());
//				
//				JPanel buttonsPanel = new JPanel();
//				buttonsPanel.setLayout(new BorderLayout());
//				
//				GridBagConstraints gbc = new GridBagConstraints();
//				gbc.insets = new Insets(5, 5, 5, 5); // Add some padding
//
//				// Add components to the incomePanel
//				gbc.gridx = 0;
//				gbc.gridy = 0;
//				gbc.anchor = GridBagConstraints.WEST;
//				gbc.gridwidth = 1;
//				gbc.fill = GridBagConstraints.NONE;
//				incomePanel.add(incomeLabel, gbc);
//
//				gbc.gridx = 1;
//				gbc.gridy = 0;
//				gbc.weightx = 1.0;
//				gbc.fill = GridBagConstraints.HORIZONTAL;
//				incomePanel.add(incomeInput, gbc);
//
//				gbc.gridx = 2;
//				gbc.gridy = 0;
//				gbc.weightx = 0.0;
//				gbc.fill = GridBagConstraints.NONE;
//				incomePanel.add(confirmIncomeButton, gbc);
//
//				gbc.gridx = 3;
//				gbc.gridy = 0;
//				incomePanel.add(loadIncomeFromFileButton, gbc);
//				
//				gbc.gridx = 0;
//				gbc.gridy = 1;
//				gbc.gridwidth = 4;
//				gbc.fill = GridBagConstraints.HORIZONTAL;
//				incomePanel.add(incomeConfirmation, gbc);
//
//				gbc.gridx = 0;
//				gbc.gridy = 0;
//				gbc.anchor = GridBagConstraints.WEST;
//				gbc.gridwidth = 1;
//				gbc.fill = GridBagConstraints.NONE;
//				expensePanel.add(expenseLabel, gbc);
//
//				gbc.gridx = 1;
//				gbc.gridy = 0;
//				gbc.weightx = 1.0;
//				gbc.fill = GridBagConstraints.HORIZONTAL;
//				expensePanel.add(expenseInput, gbc);
//
//				gbc.gridx = 2;
//				gbc.gridy = 0;
//				gbc.weightx = 0.0;
//				gbc.fill = GridBagConstraints.NONE;
//				expensePanel.add(confirmExpenseButton, gbc);
//
//				gbc.gridx = 3;
//				gbc.gridy = 0;
//				expensePanel.add(loadExpenseFromFileButton, gbc);
//			
//				gbc.gridx = 4;
//				gbc.gridy = 0;
//				expensePanel.add(saveExpenseButton, gbc);
//
//				gbc.gridx = 0;
//				gbc.gridy = 1;
//				gbc.gridwidth = 5;
//				gbc.fill = GridBagConstraints.HORIZONTAL;
//				expensePanel.add(expenseConfirmation, gbc);
//				
//				
//				buttonsPanel.add(generateReportButton, BorderLayout.NORTH);
//				buttonsPanel.add(importReportButton, BorderLayout.SOUTH);
//				
//				jframe.add(incomePanel, BorderLayout.NORTH);
//				jframe.add(expensePanel, BorderLayout.CENTER);
//				jframe.add(buttonsPanel, BorderLayout.SOUTH);
//				
//				
//				// Wrap up stuff
//				jframe.setVisible(true);
//	}
//	
//
//
//	//currently doesn't show any report, but otherwise works
//
//	private static void importReport() {
//		
//		// File chooser to choose what file to import
//		JFileChooser fileChooser = new JFileChooser();
//		String filePath = "";
//		String reportType = "";
//        FileNameExtensionFilter filter = new FileNameExtensionFilter(
//                "Comma-separated value files (.csv)", "csv");
//		fileChooser.setFileFilter(filter);
//		int returnVal = fileChooser.showSaveDialog(null);
//		if (returnVal == JFileChooser.APPROVE_OPTION) {
//			filePath = fileChooser.getSelectedFile().toString();
//		}
//		
//		// Gets path and checks report type
//		filePath = fileChooser.getSelectedFile().getPath();
//		
//		if (fileChooser.getSelectedFile().getName().toLowerCase().contains("expense")) {
//			reportType = "Expense";
//		}
//		else if (fileChooser.getSelectedFile().getName().toLowerCase().contains("income")) {
//			reportType = "Income";
//		}
//		
//		// Loads file
//		if (reportType == "Expense") {
//			expenseCalculator.loadExpenseFile(filePath);
//		}
//		else if (reportType == "Income") {
//			expenseCalculator.loadIncomeFile(filePath);
//		}
//	}
//	
//	private static void saveExpense(JTextField expenseInput) {
//	    try {
//	        createConnection();
//
//	        String sql = "INSERT INTO EXPENSE (USERNAME, CATEGORY, AMOUNT, YEARLYFREQUENCY, CURRENCYNAME) VALUES (?, ?, ?, ?, ?)";
//	        PreparedStatement pstmt = conn.prepareStatement(sql);
//
//	        String expenseText = expenseInput.getText();
//	        double expenseAmount = Double.parseDouble(expenseText);
//
//	        pstmt.setString(1, "admin");
//	        pstmt.setString(2, "genericCategory");
//	        pstmt.setDouble(3, expenseAmount);
//	        pstmt.setInt(4, 12);
//	        pstmt.setString(5, "USD");
//	        System.out.println(pstmt.executeUpdate());
//
//	        pstmt.close();
//	    } catch (Exception e) {
//	        e.printStackTrace(); 
//	    }
//	}
//	
//	private static void createConnection() {
//		try {
//			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
//			conn = DriverManager.getConnection(dbURLembedded);
//		} 
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println("hooray");
//	}
//	
//
//	// Used to select what kind of report to display 
//
//	private static void selectReport() {
//		
//		// If the current user doesn't have data, uses a test user (this is mostly in case
//		// expense and income adding isn't implemented in time)
//		ExpenseCalculator.userAtHand = currUser;
//		
//		if (ExpenseCalculator.userAtHand.getIncome() == null && 
//				ExpenseCalculator.userAtHand.getSpending() == null) {
//			
//			createTestUser();
//			
//		}
//		
//		// Initial frame settings
//		JFrame frame = new JFrame("Select Report Type:");
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setSize(new Dimension(400, 175));
//		frame.setLayout(new FlowLayout(FlowLayout.CENTER));
//		
//		// Invisible panel meant to keep the rest of the components centered
//		JPanel center = new JPanel();
//		center.setLayout(new GridBagLayout());
//		GridBagConstraints gbc = new GridBagConstraints();
//		
//		// Drop down box and array of options
//		String[] reportTypes = {
//				"Full Report", "Income Report", "Expense Report"
//		};
//		JComboBox<String> dropDownBox = new JComboBox<String>(reportTypes);
//		dropDownBox.setPreferredSize(new Dimension(250,50));
//		gbc.gridx = 0;
//		gbc.gridy = 0;
//		gbc.insets = new Insets(10, 0, 10, 0);
//		center.add(dropDownBox, gbc);
//		
//		
//		// Select button and action listener
//		JButton selectButton = new JButton("Generate Report");
//		selectButton.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				switch ((String) dropDownBox.getSelectedItem()) {
//
//				case "Full Report":
//					frame.dispose();
//					expenseCalculator.printFullReport();
//					break;
//
//				case "Income Report":
//					frame.dispose();
//					expenseCalculator.printIncomeReport();
//					break;
//
//				case "Expense Report":
//					frame.dispose();
//					expenseCalculator.printExpenseReport();
//					break;
//				}
//
//			}
//		});
//		gbc.gridy = 1;
//		center.add(selectButton, gbc);
//		
//		// Adds components and sets frame visible
//		frame.add(center);
//		frame.setVisible(true);
//		
//	}
//	
//	private static void createTestUser() {
//		ExpenseCalculator.userAtHand = new User("Test User", "Password1");
//		ExpenseCalculator.userAtHand.addIncome(new Wage("Walmart", 400.00, "May"));
//		ExpenseCalculator.userAtHand.addIncome(new Wage("Walmart", 700.00, "June"));
//		ExpenseCalculator.userAtHand.addIncome(new Wage("Erbert and Gerbert's", 500.00, "May"));
//		ExpenseCalculator.userAtHand.addIncome(new Wage("Side hustle", 10.00, "May"));
//		ExpenseCalculator.userAtHand.addIncome(new Wage("Side hustle", 40.00, "June"));
//		ExpenseCalculator.userAtHand.addExpense(new Expense("Shopping", 40.00, 1));
//		ExpenseCalculator.userAtHand.addExpense(new Expense("Subscription", 12.00, 12));
//		ExpenseCalculator.userAtHand.addExpense(new Expense("Groceries", 100.00, 24));
//	}
//		
//	
//	// used to prevent User from putting letters into a number field
//	static class NumericFilter extends DocumentFilter {
//		@Override
//		public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
//				throws BadLocationException {
//			if (string.matches("\\d+")) {
//				super.insertString(fb, offset, string, attr);
//			}
//		}
//
//		@Override
//		public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
//				throws BadLocationException {
//			if (text.matches("\\d+")) {
//				super.replace(fb, offset, length, text, attrs);
//			}
//		}
//	}
//}
//
//
