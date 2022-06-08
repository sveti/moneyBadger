package f54148.moneybadger.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
@Getter
@Setter
@NoArgsConstructor
@ToString
public class UpdateUserDTO {

    @NotBlank
    @Size(min = 4, max = 50, message="Minimal length of username is 4 and maximum is 5!")
    private String username;
    @NotBlank
    private String name;
    @NotBlank
    private String lastName;
    @NotBlank
    private String email;
}
