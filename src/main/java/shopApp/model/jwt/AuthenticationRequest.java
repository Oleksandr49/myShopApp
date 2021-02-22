package shopApp.model.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * Contains username and password.
 * To be valid, both have to be not blank String.
 * Serialized example:
 *<pre>{@code
 * {
 *      "username": "NotBlankStringValueOfPreviouslyRegisteredUser",
 *      "password": "NotBlankStringValueOfPreviouslyRegisteredUser"
 * }
 * }</pre>
 */
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

    @Getter
    @Setter
    @NotBlank
    private String username;
    @Getter
    @Setter
    @NotBlank
    private String password;

}
