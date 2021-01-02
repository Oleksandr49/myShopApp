package eCommerce.myShopApplication.model.user.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eCommerce.myShopApplication.model.item.CartItem;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;
    private Double totalCost = 0.00;
    @OneToMany(mappedBy = "cart")
    private final List<CartItem> cartItems = new ArrayList<>();
}
