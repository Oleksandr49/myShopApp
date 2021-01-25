package shopApp.model.user.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import shopApp.model.user.User;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;


@EqualsAndHashCode(callSuper = false)
@Entity
@NoArgsConstructor
@Data
public class Customer extends User {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private final Details details = new Details();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    @JsonIgnore
    private final Cart cart = new Cart();
}
