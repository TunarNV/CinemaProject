package com.example.cinemaprojectwithspring.model.response;

import com.example.cinemaprojectwithspring.model.enums.UserRole;
import com.example.cinemaprojectwithspring.model.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private String username;
    private String email;
    private UserRole userRole;
    private UserStatus status;
}
