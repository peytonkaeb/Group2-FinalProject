import java.util.ArrayList;

public class User {
	private ArrayList <Currency> currencyRates;
	private ArrayList <Wage> income;  // user income sources that user can record or view or search by type or month 
	private ArrayList <Expense> spending; //user's expenses 
	String username;
	String pwd;
	//current total income - total 
	double balance;
	// possible monthly savings, calculated using monthly income (most recent) assuming the data we have is for one year, and monthly and biweekly expenses, here you can assume yearly expenses that are recorded have already been paid. 
	double monthlySavings;	
	
	public User(String username, String password) {
        this.username = username;
        this.pwd = password;
        this.income = new ArrayList<Wage>();
        this.spending = new ArrayList<Expense>();
        this.currencyRates = new ArrayList<>();
        this.balance = 0.0;
        this.monthlySavings = 0.0;
    }

	public User() {
		
	}
	
    public void addExpense(Expense Ex) {
        spending.add(Ex);
    }

    public void addIncome(Wage W) {
        income.add(W);
    }

    public ArrayList<Wage> getIncome() {
        return income;
    }

    public ArrayList<Expense> getSpending() {
        return spending;
    }
}
