package shopApp.model.user.customer;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Embeddable
@Data
public class Address {
    @NotBlank
    private String country = "empty";
    @NotBlank
    private String state = "empty";
    @NotBlank
    private String city = "empty";
    @NotBlank
    private String street = "empty";
    @NotBlank
    private String postalCode = "empty";
}
