package urjc.dad.models;

import java.util.ArrayList;
import java.util.List;


public class DataPurchase {
	private String name;
	private String lastName;
	private String email;
	private String phone;
	private String address;
	private String bankAccount;
	private String date;
	private double totalPrice;
	private int numProducts;
	private List<LineItem> lineItems= new ArrayList<>();
	
	
	public DataPurchase() {
		
	}


	public DataPurchase(String name, String lastName, String email, String phone, String address,
			String bankAccount, String date, double totalPrice, int numProducts, List<LineItem> lineItems) {
		super();
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.bankAccount = bankAccount;
		this.date = date;
		this.totalPrice = totalPrice;
		this.numProducts = numProducts;
		this.lineItems = lineItems;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getBankAccount() {
		return bankAccount;
	}


	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public double getTotalPrice() {
		return totalPrice;
	}


	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}


	public int getNumProducts() {
		return numProducts;
	}


	public void setNumProducts(int numProducts) {
		this.numProducts = numProducts;
	}


	public List<LineItem> getLineItems() {
		return lineItems;
	}


	public void setLineItems(List<LineItem> lineItems) {
		this.lineItems = lineItems;
	}


	@Override
	public String toString() {
		return "Purchase [name=" + name + ", lastName=" + lastName + ", email=" + email + 
				", phone=" + phone + ", address=" + address + ", bankAccount=" + bankAccount + ", date=" + date
				+ ", totalPrice=" + totalPrice + ", numProducts=" + numProducts + ", lineItems=" + lineItems + "]";
	}
}
