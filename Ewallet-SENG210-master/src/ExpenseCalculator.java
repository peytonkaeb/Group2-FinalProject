import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ExpenseCalculator implements Expenser {
	
	public static User userAtHand = null;

	
	@Override
	public void addExpense(Expense Ex) {
		userAtHand.addExpense(Ex);
	}

	@Override
	public void addMonthlyIncome(Wage W) {
		userAtHand.addIncome(W);
	}

	@Override
	public void printFullReport() {
		JFrame frame = new JFrame("Full Report");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(450, 350);
		frame.setLayout(new FlowLayout(FlowLayout.CENTER));
		frame.setVisible(true);
		
		JPanel center = new JPanel();
		center.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		
		JLabel reportLabel = new JLabel("Full report for " + userAtHand.username + ":");
		constraints.gridx = 0;
		constraints.gridy = 0;
		center.add(reportLabel, constraints);
		
		JLabel incomeLabel = new JLabel("Income: ");
		constraints.gridy = 1;
		center.add(incomeLabel, constraints);
		
		constraints.gridy = 2;
		center.add(generateIncomeTable(), constraints);
		
		JLabel expenseLabel = new JLabel("Expenses: ");
		constraints.gridy = 3;
		center.add(expenseLabel, constraints);
		
		constraints.gridy = 4;
		center.add(generateExpenseTable(), constraints);
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout(FlowLayout.CENTER));
		constraints.gridy = 5;
		center.add(buttons, constraints);
		
		JButton exportReport = new JButton("Export");
		buttons.add(exportReport);
		
		JButton closeButton = new JButton("Close");
		buttons.add(closeButton);

		ActionListener buttonActions = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == closeButton) {
					frame.dispose();
				}
				if (e.getSource() == exportReport) {
					exportReport("Full Report");
				}
			}
		};
		
		exportReport.addActionListener(buttonActions);
		closeButton.addActionListener(buttonActions);
		
		frame.add(center);
		
	}

	@Override
	public void printExpenseReport() {
		JFrame frame = new JFrame("Expense Report");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(450, 350);
		frame.setLayout(new FlowLayout(FlowLayout.CENTER));
		frame.setVisible(true);
		
		JPanel center = new JPanel();
		center.setLayout(new GridBagLayout());
		frame.add(center);
		GridBagConstraints constraints = new GridBagConstraints();
		
		JLabel expenseLabel = new JLabel("Expense Report for " + userAtHand.username + ":");
		constraints.gridx = 0;
		constraints.gridy = 0;
		center.add(expenseLabel, constraints);
		
		constraints.gridy = 1;
		center.add(generateExpenseTable(), constraints);
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout(FlowLayout.CENTER));
		constraints.gridy = 2;
		center.add(buttons, constraints);
		
		JButton exportReport = new JButton("Export");
		buttons.add(exportReport);
		
		JButton filterButton = new JButton("Filter");
		buttons.add(filterButton);
		
		JButton closeButton = new JButton("Close");
		buttons.add(closeButton);

		ActionListener buttonActions = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == closeButton) {
					frame.dispose();
				}
				if (e.getSource() == filterButton) {
					printExpenseByType();
				}
				if (e.getSource() == exportReport) {
					exportReport("Expense Report");
				}
			}
		};
		
		exportReport.addActionListener(buttonActions);
		filterButton.addActionListener(buttonActions);
		closeButton.addActionListener(buttonActions);
	}
	
	private JScrollPane generateExpenseTable() {
		String[] expenseTableColumnNames = {
				"Category",
				"Amount",
				"Frequency"
		};
		
		ArrayList<String[]> expenseTableDataArrayList = new ArrayList<String[]>();
		
		// Reads data from the current user's expenses and adds to array
		for (Expense expense : userAtHand.getSpending()) {
			String[] row = new String[3];
			row[0] = expense.getCategory();
			row[1] = "$" + Double.toString(expense.getAmount());

			switch (expense.getYearlyFrequency()) {
			
			case 1:
				row[2] = "Yearly";
				break;
				
			case 12:
				row[2] = "Monthly";
				break;
				
			case 24:
				row[2] = "Biweekly";
				break;
				
			case 52:
				row[2] = "Weekly";
				break;
				
			default:
				row[2] = Integer.toString(expense.getYearlyFrequency()) + " times / year";
				break;
			}
			expenseTableDataArrayList.add(row);
		}
		
		String[][] expenseTableData = expenseTableDataArrayList.toArray(new String[0][0]);
		
		JTable expenseTable = new JTable(new ReportTableModel(expenseTableData, expenseTableColumnNames));
		
		JScrollPane tablePane = new JScrollPane(expenseTable);
		
		tablePane.setPreferredSize(new Dimension(300, 100));
				
		return tablePane;
	}

	@Override
	public void printIncomeReport() {
		JFrame frame = new JFrame("Income Report");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(450, 350);
		frame.setLayout(new FlowLayout(FlowLayout.CENTER));
		frame.setVisible(true);
		
		JPanel center = new JPanel();
		center.setLayout(new GridBagLayout());
		frame.add(center);
		GridBagConstraints constraints = new GridBagConstraints();
		
		JLabel incomeLabel = new JLabel("Income Report for " + userAtHand.username + ":");
		constraints.gridx = 0;
		constraints.gridy = 0;
		center.add(incomeLabel, constraints);
		
		constraints.gridy = 1;
		center.add(generateIncomeTable(), constraints);
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout(FlowLayout.CENTER));
		constraints.gridy = 2;
		center.add(buttons, constraints);
		
		JButton exportReport = new JButton("Export");
		buttons.add(exportReport);
		
		JButton filterButton = new JButton("Filter");
		buttons.add(filterButton);
		
		JButton closeButton = new JButton("Close");
		buttons.add(closeButton);

		ActionListener buttonActions = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == closeButton) {
					frame.dispose();
				}
				if (e.getSource() == filterButton) {
					printIncomeReportByType();
					frame.dispose();
				}
				if (e.getSource() == exportReport) {
					exportReport("Income Report");
				}
			}
		};
		
		exportReport.addActionListener(buttonActions);
		filterButton.addActionListener(buttonActions);
		closeButton.addActionListener(buttonActions);
	}
	
	// This method adds the income report as a component to a JFrame
	private JScrollPane generateIncomeTable() {
		
		String[] incomeTableColumnNames = {
				"Source",
				"Amount",
				"Month"
		};
		
		ArrayList<String[]> incomeTableDataArrayList = new ArrayList<String[]>();
		
		// Reads data from the current user's income and adds to array
		for (Wage incomeSource : userAtHand.getIncome()) {
			String[] row = new String[3];
			row[0] = incomeSource.getSource();
			row[1] = "$" + Double.toString(incomeSource.getAmount());
			row[2] = incomeSource.getMonth();
			incomeTableDataArrayList.add(row);
		}
		
		String[][] incomeTableData = incomeTableDataArrayList.toArray(new String[0][0]);
		
		JTable incomeTable = new JTable(new ReportTableModel(incomeTableData, incomeTableColumnNames));
		 
		JScrollPane tablePane = new JScrollPane(incomeTable);
		
		tablePane.setPreferredSize(new Dimension(300, 100));
				
		return tablePane;
	}

	@Override
	public void printIncomeReportByType() {

		// Initial frame settings
		JFrame frame = new JFrame("Select Report Type:");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(400, 175));
		frame.setLayout(new FlowLayout(FlowLayout.CENTER));

		// Invisible panel meant to keep the rest of the components centered
		JPanel center = new JPanel();
		center.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		ArrayList<String> incomeTypes = new ArrayList<String>();
		
		for (Wage wage : userAtHand.getIncome()) {
			if (!incomeTypes.contains(wage.getSource())) {
				incomeTypes.add(wage.getSource());
			}
		}
		
		JComboBox<String> dropDownBox = new JComboBox<String>(incomeTypes.toArray(new String[0]));
		dropDownBox.setPreferredSize(new Dimension(250,50));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(10, 0, 10, 0);
		center.add(dropDownBox, gbc);
		
		// Select button and action listener
		JButton selectButton = new JButton("Generate Report");
		selectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
				frame.getContentPane().add(center);
				center.removeAll();
				center.setLayout(new GridBagLayout());
				GridBagConstraints constraints = new GridBagConstraints();
				constraints.gridx = 0;
				constraints.gridy = 0;
				center.add(generateFilteredIncomeTable((String) dropDownBox.getSelectedItem()), constraints);
				JPanel buttons = new JPanel();
				buttons.setLayout(new FlowLayout(FlowLayout.CENTER));
				constraints.gridy = 1;
				center.add(buttons, constraints);
				
				JButton exportReport = new JButton("Export");
				buttons.add(exportReport);
				
				JButton filterButton = new JButton("Filter");
				buttons.add(filterButton);
				
				JButton closeButton = new JButton("Close");
				buttons.add(closeButton);

				ActionListener buttonActions = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (e.getSource() == closeButton) {
							frame.dispose();
						}
						if (e.getSource() == filterButton) {
							printIncomeReportByType();
							frame.dispose();
						}
						if (e.getSource() == exportReport) {
							exportReport("Income Report");
						}
					}
				};
				
				exportReport.addActionListener(buttonActions);
				filterButton.addActionListener(buttonActions);
				closeButton.addActionListener(buttonActions);
				frame.repaint();
				frame.revalidate();
				frame.pack();
			}
		});
		
		gbc.gridy = 1;
		center.add(selectButton, gbc);

		// Adds components and sets frame visible
		frame.add(center);
		frame.setVisible(true);
		
	}
	
	private JScrollPane generateFilteredIncomeTable(String selectedIncomeSource) {
		
		String[] incomeTableColumnNames = {
				"Source",
				"Amount",
				"Month"
		};
		
		ArrayList<String[]> incomeTableDataArrayList = new ArrayList<String[]>();
		
		// Reads data from the current user's income and adds to array
		for (Wage incomeSource : userAtHand.getIncome()) {
			
			if (incomeSource.getSource().equals(selectedIncomeSource)) {
				String[] row = new String[3];
				row[0] = incomeSource.getSource();
				row[1] = "$" + Double.toString(incomeSource.getAmount());
				row[2] = incomeSource.getMonth();
				incomeTableDataArrayList.add(row);
			}
			
		}
		
		String[][] incomeTableData = incomeTableDataArrayList.toArray(new String[0][0]);
		
		JTable incomeTable = new JTable(new ReportTableModel(incomeTableData, incomeTableColumnNames));
		 
		JScrollPane tablePane = new JScrollPane(incomeTable);
		
		tablePane.setPreferredSize(new Dimension(300, 100));
				
		return tablePane;
		
	}

	@Override
	public void printExpenseByType() {
		// Initial frame settings
		JFrame frame = new JFrame("Select Report Type:");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(400, 175));
		frame.setLayout(new FlowLayout(FlowLayout.CENTER));

		// Invisible panel meant to keep the rest of the components centered
		JPanel center = new JPanel();
		center.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		ArrayList<String> incomeTypes = new ArrayList<String>();

		for (Expense expense : userAtHand.getSpending()) {
			if (!incomeTypes.contains(expense.getCategory())) {
				incomeTypes.add(expense.getCategory());
			}
		}

		JComboBox<String> dropDownBox = new JComboBox<String>(incomeTypes.toArray(new String[0]));
		dropDownBox.setPreferredSize(new Dimension(250, 50));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(10, 0, 10, 0);
		center.add(dropDownBox, gbc);

		// Select button and action listener
		JButton selectButton = new JButton("Generate Report");
		selectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
				frame.getContentPane().add(center);
				center.removeAll();
				center.setLayout(new GridBagLayout());
				GridBagConstraints constraints = new GridBagConstraints();
				constraints.gridx = 0;
				constraints.gridy = 0;
				center.add(generateFilteredExpenseTable((String) dropDownBox.getSelectedItem()), constraints);
				JPanel buttons = new JPanel();
				buttons.setLayout(new FlowLayout(FlowLayout.CENTER));
				constraints.gridy = 1;
				center.add(buttons, constraints);

				JButton exportReport = new JButton("Export");
				buttons.add(exportReport);

				JButton filterButton = new JButton("Filter");
				buttons.add(filterButton);

				JButton closeButton = new JButton("Close");
				buttons.add(closeButton);

				ActionListener buttonActions = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (e.getSource() == closeButton) {
							frame.dispose();
						}
						if (e.getSource() == filterButton) {
							printExpenseByType();
							frame.dispose();
						}
						if (e.getSource() == exportReport) {
							exportReport("Income Report");
						}
					}
				};

				exportReport.addActionListener(buttonActions);
				filterButton.addActionListener(buttonActions);
				closeButton.addActionListener(buttonActions);
				frame.repaint();
				frame.revalidate();
				frame.pack();
			}
		});

		gbc.gridy = 1;
		center.add(selectButton, gbc);

		// Adds components and sets frame visible
		frame.add(center);
		frame.setVisible(true);

	}
	
	private JScrollPane generateFilteredExpenseTable(String selectedExpenseSource) {
		String[] expenseTableColumnNames = {
				"Category",
				"Amount",
				"Yearly Frequency"
		};
		
		ArrayList<String[]> expenseTableDataArrayList = new ArrayList<String[]>();
		
		// Reads data from the current user's income and adds to array
		for (Expense expense : userAtHand.getSpending()) {

			if (expense.getCategory().equals(selectedExpenseSource)) {
				String[] row = new String[3];
				row[0] = expense.getCategory();
				row[1] = "$" + Double.toString(expense.getAmount());
				switch (expense.getYearlyFrequency()) {
				
				case 1:
					row[2] = "Yearly";
					break;
					
				case 12:
					row[2] = "Monthly";
					break;
					
				case 24:
					row[2] = "Biweekly";
					break;
					
				case 52:
					row[2] = "Weekly";
					break;
					
				default:
					row[2] = Integer.toString(expense.getYearlyFrequency()) + " times / year";
					break;
				}
				expenseTableDataArrayList.add(row);
			}
			
		}
		
		String[][] expenseTableData = expenseTableDataArrayList.toArray(new String[0][0]);
		
		JTable expenseTable = new JTable(new ReportTableModel(expenseTableData, expenseTableColumnNames));
		 
		JScrollPane tablePane = new JScrollPane(expenseTable);
		
		tablePane.setPreferredSize(new Dimension(300, 100));
				
		return tablePane;
	}

	@Override
	public void exportReport(String reportTitle) {

		JFileChooser fileChooser = new JFileChooser();
		String filePath = null;
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fileChooser.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			filePath = fileChooser.getSelectedFile().toString();
		}

		File report = new File(filePath + reportTitle + ".csv");
		try {
			report.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try (FileWriter outputStream = new FileWriter(filePath + "/" + reportTitle + ".csv");
				BufferedWriter outFS = new BufferedWriter(outputStream)) {

			String reportContents = "";
			
			switch (reportTitle) {
			
			case "Full Report":
				
				reportContents += "Income\n";
				reportContents += "source,amount,month\n";
				for (Wage wage : userAtHand.getIncome()) {
					reportContents += wage.getSource() + "," + wage.getAmount() + ","
							+ wage.getMonth() + "\n";
				}
				
				reportContents += "Expenses\n";
				reportContents += "category,amount,yearly_frequency\n";
				for (Expense expense : userAtHand.getSpending()) {
					reportContents += expense.getCategory() + "," + expense.getAmount() + ","
							+ expense.getYearlyFrequency() + "\n";
				}
				break;
				
			case "Expense Report":
				reportContents += "category,amount,yearly_frequency\n";
				for (Expense expense : userAtHand.getSpending()) {
					reportContents += expense.getCategory() + "," + expense.getAmount() + ","
							+ expense.getYearlyFrequency() + "\n";
				}
				break;
				
			case "Income Report":
				reportContents += "source,amount,month\n";
				for (Wage wage : userAtHand.getIncome()) {
					reportContents += wage.getSource() + "," + wage.getAmount() + ","
							+ wage.getMonth() + "\n";
				}
				break;
				
			}
			
			outFS.write(reportContents);
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Currency convertForeignCurrency(Currency C, double amount) {
    		double exchangeRate = C.getRate();
    		double convertedAmount = amount * exchangeRate;
    		return new Currency(C.getName(), convertedAmount, exchangeRate);
	}

	@Override
	public boolean loadExpenseFile(String filePath) {
		System.out.println("loadExpenseFile() not implemented!");
		return false;
	}

	@Override
	public boolean loadIncomeFile(String filePath) {
		System.out.println("loadIncomeFile() not implemented!");
		return false;
	}

	@Override
	public int whenCanIBuy(String itemname, double price) {
    		if (userAtHand == null) {
        	System.out.println("No user loaded.");
        return -1;
    }

   	updateMonthlySavings();

    double monthlySavings = userAtHand.monthlySavings;

    	if (monthlySavings <= 0) {
        System.out.println("You are not saving any money. Try reducing expenses or increasing income.");
        return -1;
    }

   	 int months = (int) Math.ceil(price / monthlySavings);

   	 System.out.println("You can afford \"" + itemname + "\" in " + months + " month(s) based on your current monthly savings.");
   	 return months;
	}

	@Override
	public void updateMonthlySavings() {
		
		// Initialize calculators
		int totalMonthlyIncome = 0;
		int totalMonthlySpending = 0;

		// Calculate total monthly income
		for (Wage incomeSource : userAtHand.getIncome()) {
			totalMonthlyIncome += incomeSource.getAmount();
		}
		
		// Calculate total monthly spending
		for (Expense expense : userAtHand.getSpending()) {
			totalMonthlySpending += expense.getAmount();
		}
		
		// Calculate total monthly savings
		int totalMonthlySavings = totalMonthlyIncome - totalMonthlySpending;
		
		// Updates user variables
		userAtHand.balance = totalMonthlyIncome;
		userAtHand.monthlySavings = totalMonthlySavings;
		
	}

}
