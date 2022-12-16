package ru.laba5.cars.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.laba5.cars.exceptions.FileFormatException;
import ru.laba5.cars.exceptions.FileSaveException;
import ru.laba5.cars.pojo.NewsRequest;
import ru.laba5.cars.service.NewsService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/news")
@CrossOrigin(origins = "*", maxAge = 3600)
public class NewsController {
    @Autowired
    NewsService newsService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllNews() {
        return ResponseEntity.status(HttpStatus.OK).body(newsService.getAllNews());
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteNews(@PathVariable Long id) throws FileSaveException {
        return ResponseEntity.status(HttpStatus.OK).body(newsService.delete(id));
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createNews(@ModelAttribute @Valid NewsRequest newsRequest) throws FileFormatException, FileSaveException {
        return ResponseEntity.status(HttpStatus.OK).body(newsService.create(newsRequest));
    }

    @PutMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> redactNews(@ModelAttribute @Valid NewsRequest newsRequest, @PathVariable Long id) throws FileFormatException, FileSaveException {
        return ResponseEntity.status(HttpStatus.OK).body(newsService.edit(newsRequest, id));
    }

    @PutMapping("/delete_image/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> redactImage(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(newsService.deleteImage(id));
    }
}
