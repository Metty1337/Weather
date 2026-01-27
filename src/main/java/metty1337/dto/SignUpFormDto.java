package metty1337.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import metty1337.validation.PasswordMatches;

@Getter
@Setter
@PasswordMatches
@AllArgsConstructor
@NoArgsConstructor
public class SignUpFormDto {

  @NotBlank
  @Size(min = 3, max = 20)
  @Pattern(
      regexp = "^[A-Za-z0-9_.-]+$",
      message = "Only English letters, digits, '.', '_' and '-' are allowed.")
  private String username;

  @NotBlank
  @Size(min = 8, max = 20)
  private String password;

  @NotBlank
  @Size(min = 8, max = 20)
  private String repeatPassword;
}
