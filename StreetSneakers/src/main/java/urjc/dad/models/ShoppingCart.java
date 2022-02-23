package urjc.dad.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class ShoppingCart {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    @OneToOne(mappedBy="shoppingCart")
    User user;
    @OneToMany
    List<Product> listProducts = new ArrayList<>();

    public ShoppingCart() {
    }
    public ShoppingCart(User user) {
        this.user=user;
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
    public List<Product> getListProducts() {
        return listProducts;
    }
    public void setListProducts(List<Product> listProducts) {
        this.listProducts = listProducts;
    }
    @Override
    public String toString() {
        return "ShoppingCart [id=" + id + ", user=" + user + ", listProducts=" + listProducts + "]";
    }
    
}