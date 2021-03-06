package urjc.dad.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String name;
	private String lastName;
	private String email;
	private String password;
	private String phone;
	private String address;
	private String bankAccount;
	@OneToMany(mappedBy="user",cascade=CascadeType.ALL)
	private List<Purchase> purchases= new ArrayList<>();
	
	@ManyToMany
	private List<Product> wishList= new ArrayList<>();
	
	@OneToOne(cascade=CascadeType.ALL)
	ShoppingCart shoppingCart;
	
	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> roles;
	
	public User() {
		
	}
	
	public User(String name, String lastName, String email, String password) {
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}
	
	public User(String name, String lastName, String email, String password, String... roles) {
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.roles = List.of(roles);
	}
	
	public User(String name, String lastName, String email, String password, String phone, String address,
			String bankAccount) {
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.password = password;
		this.bankAccount = bankAccount;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public List<Purchase> getPurchases() {
		return purchases;
	}

	public void setPurchases(List<Purchase> orders) {
		this.purchases = orders;
	}

	public List<Product> getWishList() {
		return wishList;
	}

	public void setWishList(List<Product> wishList) {
		this.wishList = wishList;
	}

	@Override
	public String toString() {
		return name;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
}
