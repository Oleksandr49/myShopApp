package eCommerce.myShopApplication.model.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eCommerce.myShopApplication.model.user.customer.Cart;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
public class CartItem extends Item {
    @ManyToOne
    @JoinColumn(name = "cart_fk")
    @JsonIgnore
    private Cart cart;
}
