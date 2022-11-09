package com.tpagiles.dao;

import com.tpagiles.models.LicenseHolder;
import com.tpagiles.repositories.LicenseHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LicenseHolderDAOImpl implements ILicenseHolderDAO{
    @Autowired
    LicenseHolderRepository licenseHolderRepository;

    @Override
    public LicenseHolder createLicenseHolder(LicenseHolder licenseHolder) {
        return licenseHolderRepository.save(licenseHolder);
    }

    @Override
    public LicenseHolder findById(int id){
        Optional<LicenseHolder> licenseHolder = licenseHolderRepository.findById(id);
        return licenseHolder.isPresent() ? licenseHolder.get() : null;
    }

    public LicenseHolder updateLicenseHolder(LicenseHolder updatedLicenseHolder) {
        return licenseHolderRepository.save(updatedLicenseHolder);
    }
}
