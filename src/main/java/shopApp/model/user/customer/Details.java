package shopApp.model.user.customer;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shopApp.model.order.CustomerOrder;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Details {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;
    @NotBlank
    private String firstName = "empty";
    @NotBlank
    private String secondName = "empty";
    @NotBlank
    private String email = "empty";
    @Embedded
    @Valid
    private Address address = new Address();
    @OneToMany(mappedBy = "details")
    @JsonIgnore
    private final List<CustomerOrder> orderHistory = new ArrayList<>();
}
