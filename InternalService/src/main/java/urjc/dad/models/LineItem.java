package urjc.dad.models;


public class LineItem {

	private String name;
	private String description;
	private double price;
	private double size;
	private String brand;
	private String image;
	
	public LineItem() {
		
	}

	public LineItem(String name, String description, double price, double size, String brand,String image) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
		this.size = size;
		this.brand = brand;
		this.image=image;
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

	@Override
	public String toString() {
		return "LineItem [name=" + name + ", description=" + description + ", price=" + price + ", size="
				+ size + ", brand=" + brand + ", image=" + image + "]";
	}
	
}
