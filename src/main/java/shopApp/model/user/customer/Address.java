package shopApp.model.user.customer;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Embeddable
@Data
public class Address {
    @NotBlank
    private String country = "to be provided";
    @NotBlank
    private String state = "to be provided";
    @NotBlank
    private String city = "to be provided";
    @NotBlank
    private String street = "to be provided";
    @NotBlank
    private String postalCode = "to be provided";
}
