package com.tpagiles.dao;

import com.tpagiles.models.License;
import com.tpagiles.models.LicenseType;
import com.tpagiles.models.User;
import com.tpagiles.repositories.LicenseRepository;
import com.tpagiles.repositories.LicenseTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
}
