package com.tpagiles.gestores;

import com.tpagiles.dao.LicenseDAOImpl;
import com.tpagiles.dao.LicenseTypeDAOImpl;
import com.tpagiles.models.*;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
}
