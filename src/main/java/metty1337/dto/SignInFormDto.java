package metty1337.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInFormDto {
    @NotBlank
    @Size(min = 3, max = 20)
    @Pattern(regexp = "^[A-Za-z0-9_.-]+$",
            message = "Only English letters, digits, '.', '_' and '-' are allowed.")
    private String username;

    @NotBlank
    @Size(min = 8, max = 20)
    private String password;
}