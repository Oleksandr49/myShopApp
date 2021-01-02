package eCommerce.myShopApplication.model.user.customer;


import com.fasterxml.jackson.annotation.JsonIgnore;
import eCommerce.myShopApplication.model.order.CustomerOrder;
import lombok.Data;

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
