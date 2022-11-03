package com.tpagiles.controllers;

import com.tpagiles.dao.IUserDAO;
import com.tpagiles.dao.UserDAOImpl;
import com.tpagiles.gestores.GestorUser;
import com.tpagiles.models.User;
import com.tpagiles.models.dto.UserDto;
import com.tpagiles.utils.JWTUtil;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
    public void createUser(@RequestBody UserDto userDto){
        gestorUser.altaUsuario(userDto);
    }

/*
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
