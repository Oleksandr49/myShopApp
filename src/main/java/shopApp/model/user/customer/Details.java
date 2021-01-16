package shopApp.model.user.customer;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import shopApp.model.order.CustomerOrder;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Details {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String secondName;
    private Boolean isMale;
    @NotBlank
    private String email;
    @Embedded
    @Valid
    private Address address;
    @OneToMany(mappedBy = "details")
    private final List<CustomerOrder> orderHistory = new ArrayList<>();

    public Details(){
        this.firstName = "empty";
        this.secondName = "empty";
        this.email = "empty";
    }
}
