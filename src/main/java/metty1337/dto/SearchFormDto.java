package metty1337.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SearchFormDto {

  @NotBlank(message = "Enter a location.")
  @Size(min = 2, max = 64, message = "2-64 characters")
  private String name;
}
