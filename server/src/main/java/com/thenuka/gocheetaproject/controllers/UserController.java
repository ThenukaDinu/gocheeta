package com.thenuka.gocheetaproject.controllers;

import com.thenuka.gocheetaproject.dto.UserRoleDTO;
import com.thenuka.gocheetaproject.interfaces.IUserService;
import com.thenuka.gocheetaproject.modals.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserController {
    private IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity getUser(@PathVariable(value = "userId") int id) {
        try {
            if (id <= 0) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            UserRoleDTO userRoleDTO = userService.getUser(id);
            if (userRoleDTO == null) {
                return new ResponseEntity(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(userRoleDTO, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/register")
    public ResponseEntity register() {
        try {
            UserRoleDTO userRoleDTO = userService.getUser(1);
            return new ResponseEntity<>(userRoleDTO, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
