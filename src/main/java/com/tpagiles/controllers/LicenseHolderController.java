package com.tpagiles.controllers;

import com.tpagiles.gestores.GestorTitular;
import com.tpagiles.models.LicenseHolder;
import com.tpagiles.models.User;
import com.tpagiles.models.dto.LicenseHolderDto;
import com.tpagiles.models.dto.PersonFilter;
import com.tpagiles.models.dto.UserDto;
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

    @GetMapping("api/licenseholder/{id}")
    public LicenseHolder getLicenseHolder(@RequestHeader(value="Authorization") String token, @PathVariable int id){
        try{
            if(!validateToken(token)) return null;
            return gestorTitular.findById(id);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("api/licenseholders")
    public List<LicenseHolder> getFilteredLicenseHolders(@RequestBody PersonFilter filters){
        return gestorTitular.findAllWithFilters(filters);
    }

    @PostMapping("api/licenseholder/{id}")
    public ResponseEntity updateLicenseHolder(@PathVariable int id, @RequestBody LicenseHolderDto licenseHolderDto){
        LicenseHolder licenseHolder;
        try{
            licenseHolder = gestorTitular.updateLicenseHolder(id, licenseHolderDto);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        return ResponseEntity.ok(licenseHolder);
    }
}
