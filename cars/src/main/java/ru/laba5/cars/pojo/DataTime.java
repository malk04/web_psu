package ru.laba5.cars.pojo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Getter
@Component
public class DataTime {
    private LocalDateTime date;
    private int h;
    private int m;
    private int s;
    private int d;
    private int mo;
    private int y;
    public DataTime(){
        this.date=LocalDateTime.now();
        this.h=date.getHour();
        this.m=date.getMinute();
        this.s=date.getSecond();
        this.d=date.getDayOfMonth();
        this.mo=date.getMonthValue();
        this.y=date.getYear();
    }
}


