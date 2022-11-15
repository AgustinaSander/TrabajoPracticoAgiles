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

    public License emitLicense(int idLicenseHolder, int idUser, int idLicenseType) throws Exception {
        //FALTA VALIDAR SI TIENE UNA LICENCIA VIGENTE Y VER QUE HACER
        User user = gestorUser.findById(idUser);
        LicenseHolder licenseHolder = gestorTitular.findById(idLicenseHolder);
        LicenseType licenseType = findLicenseTypeById(idLicenseType);
        LocalDate expirationDate = generateExpirationDate(licenseHolder);
        License license = new License(EnumState.VIGENTE, licenseHolder, licenseType, "", user, expirationDate);
        return licenseDAO.createLicense(license);
    }

    public LicenseType findLicenseTypeById(int id){
        return licenseTypeDAO.findLicenseTypeById(id);
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
