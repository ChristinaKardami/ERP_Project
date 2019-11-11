import java.util.ArrayList;

public class Customer {
	//instance variables
	private String companyName;
	private String address;
	private int telephone;
	//constructor
	public Customer(String name, String address, int telephone) {
		this.companyName = name;
		this.address = address;
		this.telephone = telephone;
	}
	//getters and setters
	public String getCompanyName() {
		return companyName;
	}
	public void setName(String name) {
		this.companyName = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String adress) {
		this.address = adress;
	}
	public int getTelephone() {
		return telephone;
	}
	public void setTelephone(int telephone) {
		this.telephone = telephone;
	}
	//toString Method
	@Override
	public String toString() {
		return "Customer [compunyName =" + companyName + ", address=" + address + ", telephone=" + telephone
				+ "]";
	}
}