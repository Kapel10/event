package project.eventregistration.Dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDto {
    @NotBlank
    private String email;
    @NotBlank
    private String name;
    @NotBlank
    private String password;
    @NotBlank
    private String confirm_password;
    @NotBlank
    private String photo;

}
