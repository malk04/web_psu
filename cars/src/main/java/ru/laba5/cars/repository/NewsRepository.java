package ru.laba5.cars.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.laba5.cars.models.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
}
