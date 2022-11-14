package com.tpagiles.controllers;

import com.tpagiles.gestores.GestorTitular;
import com.tpagiles.gestores.GestorUser;
import com.tpagiles.models.LicenseHolder;
import com.tpagiles.models.User;
import com.tpagiles.models.dto.LicenseHolderDto;
import com.tpagiles.models.dto.UserDto;
import com.tpagiles.models.dto.UserFilter;
import com.tpagiles.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class LicenseHolderController {
    @Autowired
    GestorTitular gestorTitular;

    @Autowired
    private JWTUtil jwtUtil;

    private boolean validateToken(String token){
        String userId = jwtUtil.getKey(token);
        return userId != null;
    }

    @PostMapping("api/licenseholder")
    public ResponseEntity createLicenseHolder(@RequestBody LicenseHolderDto licenseHolderDto){
        System.out.println(licenseHolderDto);
        LicenseHolder licenseHolder;
        try {
            licenseHolder = gestorTitular.createLicenseHolder(licenseHolderDto);
        } catch (Exception ex) {
           return ResponseEntity.badRequest().body(ex.getMessage());
        }
        return ResponseEntity.ok(licenseHolder);
    }

    @GetMapping("api/licenseholders")
    public List<LicenseHolder> getLicenseHolders(){
        return gestorTitular.findAll();
    }

    /*


    @GetMapping("api/users/filter")
    public List<User> getFilteredUsers(@RequestBody UserFilter filters){
        return gestorUser.findAllWithFilters(filters);
    }
     */
}
