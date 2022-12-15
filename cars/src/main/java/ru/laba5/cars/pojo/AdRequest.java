package ru.laba5.cars.pojo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Data
public class AdRequest {
    @NotBlank(message = "Заполните поле")
    private String theme;
    @NotBlank(message = "Заполните поле")
    private String text;
    private MultipartFile file;

    public AdRequest(String theme, String text, MultipartFile file) {
        this.theme = theme;
        this.text = text;
        this.file = file;
    }
}
