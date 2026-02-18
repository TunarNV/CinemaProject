package com.example.cinemaprojectwithspring.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {
    // todo add spring data validation @NotBlank for example
    private String username;
    // todo add spring data validation @NotBlank for example
    private String password;
}
