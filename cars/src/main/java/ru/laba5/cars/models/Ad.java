package ru.laba5.cars.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ads")
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String theme;
    @Column(length = 4000)
    private String text;
    private String create_date;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    private AdFile file;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Ad(String theme, String text, String create_date) {
        this.theme = theme;
        this.text = text;
        this.create_date = create_date;
    }

    public Ad() {
    }
}
