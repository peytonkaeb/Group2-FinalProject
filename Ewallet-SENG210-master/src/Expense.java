public class Expense {
	
    private String category;
    private double amount;
    int yearlyFrequency; //1 for 1 time or once a year, 12 for monthly or or 24 for biweekly

    public Expense(String category, double amount) {
        this.category = category;
        this.amount = amount;
    }
    
    public Expense(String category, double amount, int yearlyFrequency) {
    		this.category = category;
        this.amount = amount;
        this.yearlyFrequency = yearlyFrequency;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public int getYearlyFrequency() {
		return yearlyFrequency;
	}
    
    public void setYearlyFrequency(int yearlyFrequency) {
    		this.yearlyFrequency = yearlyFrequency;
    }
}

