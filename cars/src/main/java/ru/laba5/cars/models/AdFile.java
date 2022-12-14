package ru.laba5.cars.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ad_files")
public class AdFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;
    @Column(name = "file_path")
    private String filePath;

    @OneToOne(mappedBy = "file")
    private Ad ad;

    public AdFile(String name, String type, String filePath) {
        this.name = name;
        this.type = type;
        this.filePath = filePath;
    }

    public AdFile() {
    }
}
