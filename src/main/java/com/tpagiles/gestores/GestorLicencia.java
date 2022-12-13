package com.tpagiles.gestores;

import com.tpagiles.dao.LicenseDAOImpl;
import com.tpagiles.dao.LicenseTypeDAOImpl;
import com.tpagiles.models.*;

import com.tpagiles.models.dto.LicenseFilter;
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
        List<License> currentLicenses = findLicensesByHolderId(idLicenseHolder);
        List<License> licensesWithType = currentLicenses.stream()
                .filter(l -> l.getType().getId()==Integer.parseInt(idTypeLicense))
                .collect(Collectors.toList());
        if(licensesWithType.size()>0){
            throw new Exception("The license holder with id " + idLicenseHolder + " already has a current license of type "+idTypeLicense);
        } else {
            User user = gestorUser.findById(idUser);
            LicenseHolder licenseHolder = gestorTitular.findById(idLicenseHolder);
            LicenseType licenseType = findLicenseTypeById(idTypeLicense);
            LocalDate expirationDate = generateExpirationDate(licenseHolder);
            int yearPermission = expirationDate.getYear() - LocalDate.now().getYear();
            double cost = calculateLicenseCost(yearPermission, licenseType);
            License license = new License(EnumState.VIGENTE, licenseHolder, licenseType, comments, user, LocalDate.now(), expirationDate, cost);
            return licenseDAO.createLicense(license);
        }
    }

    private double calculateLicenseCost(int yearPermission, LicenseType licenseType) {
        double cost = 0;
        switch (yearPermission){
            case 1: cost = licenseType.getPrice1();
                    break;
            case 3: cost = licenseType.getPrice2();
                    break;
            case 4: cost = licenseType.getPrice3();
                    break;
            case 5: cost = licenseType.getPrice4();
                    break;
        }
        cost += 8;
        return cost;
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

    public List<License> findAll() { return licenseDAO.findAllLicenses();}

    //Me quedo solo con las licencias expiradas
   /* public List<License> findAllExpiredLicense() {
        List<License> licenses = findAll();
        LocalDate dateToday = LocalDate.now();
        licenses = licenses.stream().filter(l -> l.getExpirationDate().isBefore(dateToday)).collect(Collectors.toList());

        return licenses;
    }*/

    public List<License> findAllFilteredLicenses(LicenseFilter filters) {
        List<License> licenses = findAll();
        if(filters.getName() != null){
            licenses = licenses.stream().filter(u -> u.getLicenseHolder().getName().equals(filters.getName()))
                    .collect(Collectors.toList());
        }
        if(filters.getSurname() != null){
            licenses = licenses.stream().filter(u -> u.getLicenseHolder().getSurname().equals(filters.getSurname()))
                    .collect(Collectors.toList());
        }
        if(filters.getTypeBlood() != null){
            licenses = licenses.stream().filter(u -> u.getLicenseHolder().getBloodType().name().equals(filters.getTypeBlood()))
                    .collect(Collectors.toList());
        }
        if(filters.getTypeRh() != null){
            licenses = licenses.stream().filter(u -> u.getLicenseHolder().getRhFactor().name().equals(filters.getTypeRh()))
                    .collect(Collectors.toList());
        }
        if(filters.getDonor() != null){
            licenses = licenses.stream().filter(u -> u.getLicenseHolder().getIsOrganDonor().equals(filters.getDonor()))
                    .collect(Collectors.toList());
        }
        return licenses;
    }
}
