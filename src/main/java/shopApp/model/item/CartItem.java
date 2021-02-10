package shopApp.model.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import shopApp.model.user.customer.Cart;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
public class CartItem extends Item {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_fk")
    @JsonIgnore
    private Cart cart;
}
