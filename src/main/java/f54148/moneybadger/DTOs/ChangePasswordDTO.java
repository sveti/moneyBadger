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
public class ChangePasswordDTO {

    @NotBlank
    @Size(min = 5, message="Password must be at least 5 characters long!")
    private String currentPassword;

    @NotBlank
    @Size(min = 5, message="Password must be at least 5 characters long!")
    private String newPassword;

    @NotBlank
    @Size(min = 5, message="Password must be at least 5 characters long!")
    private String repeatNewPassword;
}
