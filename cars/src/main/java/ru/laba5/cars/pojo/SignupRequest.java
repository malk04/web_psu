package ru.laba5.cars.pojo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignupRequest {
    @NotBlank(message = "Заполните поле")
    private String username;

    @NotBlank(message = "Заполните поле")
    @Size(min = 4, max = 20, message = "Пароль должен содержать от 4 до 20 символов")
    private String password;

    @NotBlank(message = "Заполните поле")
    @Email(message = "Неправильная почта")
    private String email;

    public SignupRequest(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
