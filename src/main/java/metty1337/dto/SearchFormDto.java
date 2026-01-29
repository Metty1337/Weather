package metty1337.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchFormDto {

  @NotBlank
  @Size(min = 3, max = 20)
  @Pattern(regexp = "^[A-Za-z0-9_.-]+$", message = "Only English letters, digits, '.', '_' and '-' are allowed.")
  private String name;
}
