package eCommerce.myShopApplication.model.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eCommerce.myShopApplication.model.item.OrderItem;
import eCommerce.myShopApplication.model.user.customer.Details;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private LocalDateTime created;
    private Double totalCost;
    private OrderState orderState;

    @ManyToOne
    @JoinColumn(name = "details_fk")
    @JsonIgnore
    private Details details;

    @OneToMany(mappedBy = "customerOrder")
    private final List<OrderItem> orderItems = new ArrayList<>();
}
