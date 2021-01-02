package eCommerce.myShopApplication.model.user.customer;

import eCommerce.myShopApplication.model.user.User;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Customer extends User {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private final Details details = new Details();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private final Cart cart = new Cart();

    public Customer(){
        super();
    }

    public Details getDetails() {
        return details;
    }

    public Cart getShoppingCart() {
        return cart;
    }
}
