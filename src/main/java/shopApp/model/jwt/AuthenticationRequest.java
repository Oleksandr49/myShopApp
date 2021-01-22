package shopApp.model.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

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
