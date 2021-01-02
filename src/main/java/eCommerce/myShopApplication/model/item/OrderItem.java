package eCommerce.myShopApplication.model.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eCommerce.myShopApplication.model.order.CustomerOrder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
public class OrderItem extends Item {
    @ManyToOne
    @JoinColumn(name = "customerOrder_fk")
    @JsonIgnore
    private CustomerOrder customerOrder;
}
