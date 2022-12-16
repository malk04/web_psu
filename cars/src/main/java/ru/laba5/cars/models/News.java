package ru.laba5.cars.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 4000)
    private String text;

    private String date;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private NewsImage image;

    public News(String title, String text, String date) {
        this.title = title;
        this.text = text;
        this.date = date;
    }

    public News() {
    }
}
