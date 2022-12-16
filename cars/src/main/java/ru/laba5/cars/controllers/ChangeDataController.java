package ru.laba5.cars.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.laba5.cars.models.ERole;
import ru.laba5.cars.models.Role;
import ru.laba5.cars.models.User;
import ru.laba5.cars.pojo.MessageResponse;
import ru.laba5.cars.pojo.UserChange;
import ru.laba5.cars.repository.RoleRepository;
import ru.laba5.cars.repository.UserRepository;

import java.util.Iterator;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/data")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ChangeDataController {

    @Autowired
    UserRepository userRespository;

    @Autowired
    RoleRepository roleRepository;


    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> getAllUsers() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return userRespository.findAll();
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteUser(@RequestBody UserChange userChange) {
        User user = userRespository.findByUsername(userChange.getUsername()).get();
        userRespository.delete(user);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Пользователь успешно удален"));
    }

    @PostMapping("/add_role")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addRole(@RequestBody UserChange userChange) {

        User user = userRespository.findByUsername(userChange.getUsername()).get();
        Set<Role> roles = user.getRoles();
        Role userRole;
        switch (userChange.getUserrole()) {
            case  ("ROLE_ADMIN"):
                userRole = roleRepository
                        .findByName(ERole.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error, Role ADMIN is not found"));
                roles.add(userRole);
                break;
            case ("ROLE_USER"):
                userRole = roleRepository
                        .findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
                roles.add(userRole);
                break;
            case ("ROLE_MODERATOR"):
                userRole = roleRepository
                        .findByName(ERole.ROLE_MODERATOR)
                        .orElseThrow(() -> new RuntimeException("Error, Role ADMIN is not found"));
                roles.add(userRole);
                break;
        }
        user.setRoles(roles);
        userRespository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Роль добавлена"));
    }

    @PostMapping("/del_role")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteRole(@RequestBody UserChange userChange) {
        User user = userRespository.findByUsername(userChange.getUsername()).get();
        Set<Role> roles = user.getRoles();
        Iterator<Role> it = roles.iterator();

        while (it.hasNext()) {
            if (it.next().getName().toString().equals(userChange.getUserrole())){
                it.remove();
                break;
            }
        }
        user.setRoles(roles);
        userRespository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Роль удалена"));
    }
}
