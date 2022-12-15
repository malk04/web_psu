package ru.laba5.cars.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.laba5.cars.models.Ad;
import ru.laba5.cars.models.User;

import java.util.List;

public interface AdRepository extends JpaRepository<Ad, Long> {
    List<Ad> findAllAdsByUser(User user);
}
