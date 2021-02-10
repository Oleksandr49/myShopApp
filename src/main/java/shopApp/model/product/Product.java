package shopApp.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import shopApp.model.item.Item;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String productName;
    @Min(0)
    @NotNull
    private Integer productPrice;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Item> item;
}
