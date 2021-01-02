package eCommerce.myShopApplication.model.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eCommerce.myShopApplication.model.item.OrderItem;
import eCommerce.myShopApplication.model.user.customer.Details;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private LocalDateTime created;
    private Double totalCost;
    private OrderState orderState;

    @ManyToOne
    @JoinColumn(name = "details_fk")
    private Details details;

    @OneToMany(mappedBy = "customerOrder")
    private final List<OrderItem> orderItems = new ArrayList<>();

    public Long getId() {
        return orderId;
    }

    public void setId(Long id) {
        this.orderId = id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    @JsonIgnore
    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
}
