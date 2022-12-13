package com.tpagiles.controllers;

import com.tpagiles.gestores.GestorLicencia;
import com.tpagiles.models.License;
import com.tpagiles.models.dto.LicenseFilter;
import com.tpagiles.models.LicenseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class LicenseController {
    @Autowired
    GestorLicencia gestorLicencia;

    @PostMapping("api/license")
    public ResponseEntity emitLicense(@RequestBody LicenseInfo info){
        License license;
        try {
            license = gestorLicencia.emitLicense(info.getIdLicenseHolder(), info.getIdUser(), info.getNameTypeLicense(), info.getComments());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        return ResponseEntity.ok(license);
    }

    @GetMapping("api/license")
    public List<License> getCurrentLicenses(){
        return gestorLicencia.findAllCurrent();
    }

    @PostMapping("api/licenses")
    public List<License> getFilteredLicenses(@RequestBody LicenseFilter filters){
        return gestorLicencia.findAllFilteredLicenses(filters);
    }

    @PostMapping("api/update")
    public void updateLicenseStates(){
        gestorLicencia.updateStates();
    }

}
