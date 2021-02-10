package shopApp.model.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shopApp.model.item.OrderItem;
import shopApp.model.user.customer.Details;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private LocalDateTime created;
    private Integer totalCost;
    private OrderState orderState;

    @ManyToOne
    @JoinColumn(name = "details_fk")
    @JsonIgnore
    private Details details;

    @OneToMany(mappedBy = "customerOrder", cascade = CascadeType.ALL)
    private final List<OrderItem> orderItems = new ArrayList<>();
}
