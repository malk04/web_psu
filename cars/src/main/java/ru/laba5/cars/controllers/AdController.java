package ru.laba5.cars.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.laba5.cars.exceptions.FileFoundException;
import ru.laba5.cars.exceptions.FileSaveException;
import ru.laba5.cars.pojo.AdRequest;
import ru.laba5.cars.service.AdService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/ads")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdController {
    @Autowired
    AdService adService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createAd(@ModelAttribute @Valid AdRequest adRequest) throws FileSaveException, FileFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(adService.createAd(adRequest));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteLot(@PathVariable Long id) throws FileSaveException {
        return ResponseEntity.status(HttpStatus.OK).body(adService.deleteAd(id));
    }

    @GetMapping("/getMyAllAds")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllUserAds() {
        return ResponseEntity.status(HttpStatus.OK).body(adService.getAllUserAds());
    }

    @PutMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> editAd(@ModelAttribute @Valid AdRequest adRequest, @PathVariable Long id) throws FileSaveException {
        return ResponseEntity.status(HttpStatus.OK).body(adService.editAd(adRequest, id));
    }

}
