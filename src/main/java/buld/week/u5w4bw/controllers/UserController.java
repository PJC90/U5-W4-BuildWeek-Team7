package buld.week.u5w4bw.controllers;

import buld.week.u5w4bw.entities.User;
import buld.week.u5w4bw.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<User> usersList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "userId") String order) {
        return userService.findAll(page, size, order);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public User uniqueUser(@PathVariable UUID userId) {
        return userService.findById(userId);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User userUpdate(@PathVariable UUID userId, @RequestBody User body) {
        return userService.userUpdate(userId, body);
    }


    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority(ADMIN)")
    public void deleteUser(@PathVariable UUID userId) {
        userService.userDelete(userId);
    }


    //------------------ /me --------------------\\


    @GetMapping("/me")
    public User profilePage(@AuthenticationPrincipal User utente) {
        return utente;
    }


    @PutMapping("/me")
    public User updateUser(@AuthenticationPrincipal User userId,@RequestBody User body){
        return userService.userUpdate(userId.getUserId(),body);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void cancellaUtente(@AuthenticationPrincipal User userId) {
        userService.userDelete(userId.getUserId());
    }

    @PatchMapping("/{userId}/upload")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public String uploadAvatarImg(@RequestParam("image") MultipartFile file, @PathVariable UUID userId) throws Exception {
        return userService.uploadImage(file, userId);
    }
}
