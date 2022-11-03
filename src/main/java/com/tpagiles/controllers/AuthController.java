package com.tpagiles.controllers;

import com.tpagiles.dao.IUserDAO;
import com.tpagiles.models.User;
import com.tpagiles.utils.JWTUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    @Autowired
    private IUserDAO userDAO;
    @Autowired
    private JWTUtil jwtUtil;

    /*@PostMapping("api/login")
    public String login(@RequestBody User user){
        User u = userDAO.findUserByEmail(user);

        if(u != null){
            String tokenJWT = jwtUtil.create(String.valueOf(u.getId()), u.getEmail());
            return tokenJWT;
        }
        return "FAIL";
    }
*/
}
