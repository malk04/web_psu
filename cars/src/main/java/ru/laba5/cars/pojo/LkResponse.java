package ru.laba5.cars.pojo;

import lombok.Data;

import java.util.Collection;

@Data
public class LkResponse {
    private String username;
    private int visits;
    private Collection<?> roles;

    public LkResponse(String username, int visits, Collection<?> roles) {
        this.username = username;
        this.visits = visits;
        this.roles = roles;
    }
}
