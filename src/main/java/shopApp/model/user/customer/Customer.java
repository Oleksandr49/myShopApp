package shopApp.model.user.customer;

import lombok.Data;
import lombok.NoArgsConstructor;
import shopApp.model.user.User;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@NoArgsConstructor
@Data
public class Customer extends User {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private final Details details = new Details();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private final Cart cart = new Cart();
}
