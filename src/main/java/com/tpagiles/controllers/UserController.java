package com.tpagiles.controllers;

import com.tpagiles.gestores.GestorUser;
import com.tpagiles.models.User;
import com.tpagiles.models.dto.UserDto;
import com.tpagiles.models.dto.UserFilter;
import com.tpagiles.utils.JWTUtil;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping("api/users")
    public List<User> getUsers(){
        return gestorUser.findAll();
    }

    @PostMapping("api/users")
    public List<User> getFilteredUsers(@RequestBody UserFilter filters){
        return gestorUser.findAllWithFilters(filters);
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
