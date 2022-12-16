package ru.laba5.cars.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.laba5.cars.models.NewsImage;

@Repository
public interface NewsImageRepository extends JpaRepository<NewsImage, Long> {
}
