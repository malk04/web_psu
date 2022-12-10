package ru.laba5.cars.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.laba5.cars.models.User;
import ru.laba5.cars.pojo.MessageResponse;
import ru.laba5.cars.repository.UserRepository;
import ru.laba5.cars.service.UserDetailsImpl;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/avatar")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AvatarController {
    @Autowired
    UserRepository userRepository;
    private final String path = "D:\\3 semestr\\web_psu\\cars_front\\avatars\\";

    @PutMapping("/upload")
    public ResponseEntity<?> postAvatar(@ModelAttribute MultipartFile image) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).get();
        if (image.getContentType().equalsIgnoreCase("image/png") ||
                image.getContentType().equalsIgnoreCase("image/jpeg")) {
            String filePath = path + UUID.randomUUID();
            try {
                image.transferTo(new File(filePath));
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse("Ошибка сохранения изображения"));
            }
            user.setImage(filePath);
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Фото добавлено успешно"));
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new MessageResponse("Недопустимый формат файла"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAvatar() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String filePath = userDetails.getImage();
        if (filePath == null) {
            filePath = "";
        }
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(filePath));
    }
}
