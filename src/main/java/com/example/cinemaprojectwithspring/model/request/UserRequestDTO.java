package com.example.cinemaprojectwithspring.model.request;

import com.example.cinemaprojectwithspring.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    // todo add spring data validation @NotBlank for example
    private String username;
    // todo add spring data validation @NotBlank for example
    private String password;
    // todo add spring data validation @NotBlank & @Email for example
    private String email;
}
