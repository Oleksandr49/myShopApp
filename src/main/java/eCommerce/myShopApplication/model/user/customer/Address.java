package eCommerce.myShopApplication.model.user.customer;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class Address {
    private String country;
    private String state;
    private String city;
    private String street;
    private String postalCode;
}
