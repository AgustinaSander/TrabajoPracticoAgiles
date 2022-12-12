package com.tpagiles.gestores;

import com.tpagiles.dao.LicenseDAOImpl;
import com.tpagiles.dao.LicenseTypeDAOImpl;
import com.tpagiles.models.*;
import com.tpagiles.models.dto.PersonFilter;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GestorLicencia {
    @Autowired
    LicenseDAOImpl licenseDAO;
    @Autowired
    LicenseTypeDAOImpl licenseTypeDAO;

    @Autowired
    GestorUser gestorUser;
    @Autowired
    GestorTitular gestorTitular;

    public License emitLicense(int idLicenseHolder, int idUser, String idTypeLicense, String comments) throws Exception {
        User user = gestorUser.findById(idUser);
        LicenseHolder licenseHolder = gestorTitular.findById(idLicenseHolder);
        LicenseType licenseType = findLicenseTypeById(idTypeLicense);
        LocalDate expirationDate = generateExpirationDate(licenseHolder);
        License license = new License(EnumState.VIGENTE, licenseHolder, licenseType, comments, user, LocalDate.now(), expirationDate);
        return licenseDAO.createLicense(license);
    }

    public LicenseType findLicenseTypeById(String id){
        return licenseTypeDAO.findLicenseTypeById(Integer.valueOf(id));
    }

    public LicenseType findLicenseTypeByName(String name){
        return licenseTypeDAO.findLicenseTypeByName(name);
    }

    public List<License> findLicensesByTypeByHolderId(int idLicenseHolder, int idLicenseType){
        return licenseDAO.findLicensesByTypeByHolderId(idLicenseHolder, idLicenseType);
    }

    private LocalDate generateExpirationDate(LicenseHolder licenseHolder){
        return LocalDate.now().plusYears(4);
    }

    public int getMinAgeByType(String type) {
        return licenseTypeDAO.getMinAgeByType(type);
    }

    //Busco todas las licencias
    public List<License> findAll() { return licenseDAO.findAllLicenses();}

    //Me quedo solo con las licencias expiradas
    public List<License> findAllExpiredLicense() {
        List<License> licenses = findAll();
        LocalDate dateToday = LocalDate.now();
        licenses = licenses.stream().filter(l -> l.getExpirationDate().isBefore(dateToday)).collect(Collectors.toList());

        return licenses;
    }
}
