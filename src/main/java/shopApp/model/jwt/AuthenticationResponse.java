package shopApp.model.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Response object for user authentication.
 * Contains JWT token value. Serialized example:
 *<pre>{@code
 * {
 *      "jwt": "JwtTokenValueString"
 * }
 * }</pre>
 */
@AllArgsConstructor
public class AuthenticationResponse {
    @Getter
    private final String jwt;
}
