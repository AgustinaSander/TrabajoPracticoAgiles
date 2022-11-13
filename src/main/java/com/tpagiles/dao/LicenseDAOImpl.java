package com.tpagiles.dao;

import com.tpagiles.models.License;
import com.tpagiles.models.LicenseType;
import com.tpagiles.models.User;
import com.tpagiles.repositories.LicenseRepository;
import com.tpagiles.repositories.LicenseTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LicenseDAOImpl implements ILicenseDAO{
    @Autowired
    LicenseRepository licenseRepository;
    @Autowired
    LicenseTypeRepository licenseTypeRepository;

    @Override
    public License createLicense(License license) {
        return licenseRepository.save(license);
    }

    public LicenseType findLicenseTypeById(int id){
        Optional<LicenseType> type = licenseTypeRepository.findById(id);
        return type.isPresent() ? type.get() : null;
    }

    public int getMinAgeByType(String type) {
        List<LicenseType> types = findAllTypes();
        LicenseType licenseType = types.stream().filter(t -> t.getName().equals(type)).collect(Collectors.toList()).get(0);
        return licenseType.getMinAge();
    }

    public List<LicenseType> findAllTypes(){
        return (List<LicenseType>) licenseTypeRepository.findAll();
    }
}
