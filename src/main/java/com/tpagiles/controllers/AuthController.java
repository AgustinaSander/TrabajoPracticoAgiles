package com.tpagiles.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tpagiles.dao.IUserDAO;
import com.tpagiles.gestores.GestorUser;
import com.tpagiles.models.User;
import com.tpagiles.utils.JWTUtil;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class AuthController {
    @Autowired
    private GestorUser gestorUser;
    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("api/login")
    public ResponseEntity<List<String>> loginUser(@RequestBody ObjectNode JSONObject) throws Exception {
        //Se loguea con type, identification y password
        String type = JSONObject.get("type").asText();
        String identification = JSONObject.get("identification").asText();
        String password = JSONObject.get("password").asText();

        List<String> response = new ArrayList<>();
        try {
            User u = gestorUser.findByTypeAndIdentification(type, identification, password);
            if (u != null) {
                String tokenJWT = jwtUtil.create(String.valueOf(u.getId()), u.getIdentification());
                response.add(tokenJWT);
                response.add(String.valueOf(u.getId()));
            }
        } catch (Exception ex) {
            response.add("Los datos son incorrectos.");
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }
}
