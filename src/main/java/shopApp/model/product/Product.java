package shopApp.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import shopApp.model.item.Item;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * POJO entity object.
 * Template for serialized Product object:
 * <pre>{@code
 * {
 *      "id": LongValue,
 *      "productName": "NotBlankStringValue",
 *      "productPrice": IntegerValue >= 0,
 *      "_links": {
 *          "self: {
 *              "href": "StringValue"
 *          }
 *      }
 * }
 * }</pre>
 * Integer value is used As "productPrice" for better precision.
 * So, for example 1.00(currency) = 100 as field value, 0.53(currency) = 53 as field value.
 */
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
