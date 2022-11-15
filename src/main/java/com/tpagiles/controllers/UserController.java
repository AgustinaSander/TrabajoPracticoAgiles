package com.tpagiles.controllers;

import com.tpagiles.gestores.GestorUser;
import com.tpagiles.models.User;
import com.tpagiles.models.dto.UserDto;
import com.tpagiles.models.dto.PersonFilter;
import com.tpagiles.utils.JWTUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    GestorUser gestorUser;

    @Autowired
    private JWTUtil jwtUtil;

    private boolean validateToken(String token){
        String userId = jwtUtil.getKey(token);
        return userId != null;
    }

    @PostMapping("api/user")
    public ResponseEntity createUser(@RequestBody UserDto userDto){
        User user;
        try {
            user = gestorUser.createUser(userDto);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("api/user/{id}")
    public void deleteUser(@PathVariable int id){
        try {
            gestorUser.deleteUser(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("api/user/{id}")
    public User getUser(@RequestHeader(value="Authorization") String token, @PathVariable int id){
        try{
            if(!validateToken(token)) return null;
            return gestorUser.findById(id);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("api/users")
    public List<User> getUsers(){
        return gestorUser.findAll();
    }

    @PostMapping("api/users")
    public List<User> getFilteredUsers(@RequestBody PersonFilter filters){
        return gestorUser.findAllWithFilters(filters);
    }

    @PostMapping("api/user/{id}")
    public ResponseEntity updateUser(@PathVariable int id, @RequestBody UserDto userDto){
        User user;
        try{
            user = gestorUser.updateUser(id, userDto);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        return ResponseEntity.ok(user);
    }
/*
SOLO ESTA PARA VER LO DEL TOKEN SI DESPUES LO PONEMOS
    @GetMapping("api/user")
    public List<User> getUsers(@RequestHeader(value="Authorization") String token){
        if(!validateToken(token)) return null;
        return userDAO.findAllUsers();
    }

    @DeleteMapping("api/user/{id}")
    public void deleteUserById(@RequestHeader(value="Authorization") String token, @PathVariable Long id){
        if(!validateToken(token)) return;
        
        userDAO.deleteUser(id);
    }
*/
}
