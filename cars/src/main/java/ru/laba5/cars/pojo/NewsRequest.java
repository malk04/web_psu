package ru.laba5.cars.pojo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Data
public class NewsRequest {
    @NotBlank(message = "Заполните поле")
    private String title;
    @NotBlank(message = "Заполните поле")
    private String text;
    private MultipartFile image;

    public NewsRequest(String title, String text, MultipartFile image) {
        this.title = title;
        this.text = text;
        this.image = image;
    }
}
