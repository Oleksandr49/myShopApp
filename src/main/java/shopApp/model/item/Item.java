package shopApp.model.item;


import lombok.Data;
import shopApp.model.product.Product;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Transient
    private Product product;
    @Min(0)
    @NotNull
    private Integer amount;
    @Min(0)
    @NotNull
    private Integer cost;

}
