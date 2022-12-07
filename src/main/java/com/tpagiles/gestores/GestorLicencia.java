package com.tpagiles.gestores;

import com.tpagiles.dao.LicenseDAOImpl;
import com.tpagiles.dao.LicenseTypeDAOImpl;
import com.tpagiles.models.*;
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
        return licenseTypeDAO.findLicenseTypeById(Integer.parseInt(id));
    }

    public LicenseType findLicenseTypeByName(String name){
        return licenseTypeDAO.findLicenseTypeByName(name);
    }

    public List<License> findLicensesByTypeByHolderId(int idLicenseHolder, int idLicenseType){
        return licenseDAO.findLicensesByTypeByHolderId(idLicenseHolder, idLicenseType);
    }

    public List<License> findLicensesByHolderId(int idLicenseHolder){
        return licenseDAO.findLicensesByHolderId(idLicenseHolder);
    }

    public LocalDate generateExpirationDate(LicenseHolder licenseHolder){
        int expirationDay = licenseHolder.getBirthDate().getDayOfMonth();
        int expirationMonth = licenseHolder.getBirthDate().getMonthValue();
        int expirationYear;
        int age = licenseHolder.getAge();
        if(age > 70)
            expirationYear = LocalDate.now().getYear()+1;
        else if(age<=70 && age>=61)
            expirationYear = LocalDate.now().getYear()+3;
        else if(age<=60 && age>=47)
            expirationYear = LocalDate.now().getYear()+4;
        else if(age<=46 && age>=21)
            expirationYear = LocalDate.now().getYear()+5;
        else {
            expirationYear = isFirstTimeGettingLicense(licenseHolder) ? LocalDate.now().getYear()+1 : LocalDate.now().getYear()+3;
        }

        return LocalDate.of(expirationYear, expirationMonth, expirationDay);
    }

    private boolean isFirstTimeGettingLicense(LicenseHolder licenseHolder) {
        return findLicensesByHolderId(licenseHolder.getId()).size()==0;
    }

    public int getMinAgeByType(String type) {
        return licenseTypeDAO.getMinAgeByType(type);
    }
}
