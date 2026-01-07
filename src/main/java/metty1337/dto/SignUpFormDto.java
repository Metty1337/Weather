package metty1337.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpFormDto {
    private String username;
    private String password;
    private String repeatPassword;
}
