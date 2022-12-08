package ru.laba5.cars.pojo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
    @NotBlank(message = "Заполните поле")
    private String username;
    @NotBlank(message = "Заполните поле")
    private String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
