package urjc.dad.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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
	private String date;
	private double totalPrice;
	@OneToMany(cascade=CascadeType.ALL)
	private List<LineItem> lineItems= new ArrayList<>();
	
	public Purchase() {
		
	}

	public Purchase(User user, String date, double totalPrice, List<LineItem> lineItems) {
		this.user = user;
		this.date = date;
		this.totalPrice = totalPrice;
		this.lineItems = lineItems;
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

	public List<LineItem> getProducts() {
		return lineItems;
	}

	public void setProducts(List<LineItem> lineItems) {
		this.lineItems = lineItems;
	}

	@Override
	public String toString() {
		return "Purchase [id=" + id + ", user=" + user + ", date=" + date + ", totalPrice=" + totalPrice
				+ ", lineItems=" + lineItems + "]";
	}
	
}
