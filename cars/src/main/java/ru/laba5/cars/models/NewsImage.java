package ru.laba5.cars.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "news_images")
public class NewsImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;

    @Column(name = "file_path")
    private String filePath;

    @OneToOne(mappedBy = "image")
    private News news;

    public NewsImage(String name, String type, String filePath) {
        this.name = name;
        this.type = type;
        this.filePath = filePath;
    }

    public NewsImage() {
    }
}
