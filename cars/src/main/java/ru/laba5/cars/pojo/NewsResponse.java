package ru.laba5.cars.pojo;

import lombok.Data;

@Data
public class NewsResponse {
    private Long id;
    private String title;
    private String text;
    private String date;
    private String fileName;
    private String filePath;

    public NewsResponse(Long id, String title, String text, String date, String fileName, String filePath) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.date = date;
        this.fileName = fileName;
        this.filePath = filePath;
    }
}
