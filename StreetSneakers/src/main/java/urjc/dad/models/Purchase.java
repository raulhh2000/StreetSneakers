package urjc.dad.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Purchase {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	@ManyToOne
	private User user;
	private LocalDateTime date;
	private double totalPrice;
	@OneToMany
	private List<Product> products= new ArrayList<>();
	
	public Purchase() {
		
	}

	public Purchase(User user, LocalDateTime date, double totalPrice, List<Product> products) {
		this.user = user;
		this.date = date;
		this.totalPrice = totalPrice;
		this.products = products;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "Purchase [id=" + id + ", user=" + user + ", date=" + date + ", totalPrice=" + totalPrice + ", products="
				+ products + "]";
	}
	
}
