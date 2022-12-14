package ru.laba5.cars.pojo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class AdRequest {
    @NotBlank(message = "Заполните поле")
    private String theme;
    @NotBlank(message = "Заполните поле")
    @Size(max = 10000, message = "Слишком много текста")
    private String text;
    private MultipartFile file;

    public AdRequest(String theme, String text, MultipartFile file) {
        this.theme = theme;
        this.text = text;
        this.file = file;
    }
}
