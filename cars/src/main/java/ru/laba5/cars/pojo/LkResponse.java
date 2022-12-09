package ru.laba5.cars.pojo;

import lombok.Data;

@Data
public class LkResponse {
    private String username;
    private int visits;
    private String image;
    private String roles;

    public LkResponse(String username, int visits, String image, String roles) {
        this.username = username;
        this.visits = visits;
        this.image = image;
        this.roles = roles;
    }
}
