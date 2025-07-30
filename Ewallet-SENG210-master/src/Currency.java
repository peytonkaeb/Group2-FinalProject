
public class Currency {
	public double rate;
	public String name;
	public double convertedAmount;
	
	public Currency(String name, double convertedAmount, double exchangeRate){
		this.name = name;
		this.rate = exchangeRate;
		this.convertedAmount = convertedAmount;		
	}

	public double getRate() {
		return rate;
	}

	public String getName() {
		return name;
	}
	
}
