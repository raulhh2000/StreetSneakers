package urjc.dad.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String name;
	private String description;
	private double price;
	private double size;
	private String brand;
	private String image;
	@OneToMany(mappedBy="product", cascade=CascadeType.ALL)
	private List<Review> reviews= new ArrayList<>();
	
	public Product() {
		
	}
	
	public Product(String name, String description, double price, double size, String brand) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.size = size;
		this.brand = brand;
	}
	
	

	public Product(String name, String description, double price, double size, String brand, String image) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
		this.size = size;
		this.brand = brand;
		this.image = image;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price + ", size="
				+ size + ", brand=" + brand + ", image=" + image + ", reviews=" + reviews + "]";
	}
	
}
