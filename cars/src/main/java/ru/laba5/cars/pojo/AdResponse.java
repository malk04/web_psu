package ru.laba5.cars.pojo;

import lombok.Data;

@Data
public class AdResponse {
    private Long id;
    private String theme;
    private String text;
    private String fileName;
    private String create_date;

    public AdResponse(Long id, String theme, String text, String fileName, String create_date) {
        this.id = id;
        this.theme = theme;
        this.text = text;
        this.fileName = fileName;
        this.create_date = create_date;
    }
}
