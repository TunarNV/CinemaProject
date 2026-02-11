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
    private String username;
    private String password;
    private String email;
}
