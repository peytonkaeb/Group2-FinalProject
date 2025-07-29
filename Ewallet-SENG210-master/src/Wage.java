
public class Wage {
	
	// Default fields
	private String source;
	private double amount;
	private String month;
	
	// Fields for calculating with an hourly rate and hours worked
    private double hourlyRate;
    private int hoursWorked;

    public Wage(double hourlyRate, int hoursWorked) {
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }
    
    public Wage(String source, double amount, String month) {
    		this.source = source;
    		this.amount = amount;
    		this.month = month;
    }
    
    public Wage(String source, double amount) {
    		this.source = source;
		this.amount = amount;
    }
    
    public String getSource() {
    		return source;
    }
    
    public void setSource(String source) {
    		this.source = source;
    }
    
    public double getAmount() {
    		return amount;
    }
    
    public void setAmount(double amount) {
    		this.amount  = amount;
    }
    
    public String getMonth() {
    		return month;
    }
    
    public void setMonth(String month) {
    		this.month = month;
    }
    
    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(int hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public double calculateWage() {
        return hourlyRate * hoursWorked;
    }
}

