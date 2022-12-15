package ru.laba5.cars.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.laba5.cars.models.AdFile;

public interface AdFileRepository extends JpaRepository<AdFile, Long> {
}
