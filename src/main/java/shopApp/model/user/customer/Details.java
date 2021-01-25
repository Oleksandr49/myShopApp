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
    private String firstName = "to be provided";
    @NotBlank
    private String secondName = "to be provided";
    private Boolean isMale;
    @NotBlank
    private String email = "to be provided";
    @Embedded
    @Valid
    private Address address = new Address();
    @OneToMany(mappedBy = "details")
    @JsonIgnore
    private final List<CustomerOrder> orderHistory = new ArrayList<>();
}
