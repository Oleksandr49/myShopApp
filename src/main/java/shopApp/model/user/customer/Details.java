package shopApp.model.user.customer;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import shopApp.model.order.CustomerOrder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Details {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;
    private String firstName;
    private String secondName;
    private Boolean isMale;
    private String email;
    @Embedded
    private Address address;
    @OneToMany(mappedBy = "details")
    private final List<CustomerOrder> orderHistory = new ArrayList<>();
}
