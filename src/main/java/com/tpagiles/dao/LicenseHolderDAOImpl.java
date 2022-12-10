package com.tpagiles.dao;

import com.tpagiles.models.License;
import com.tpagiles.models.LicenseHolder;
import com.tpagiles.models.User;
import com.tpagiles.repositories.LicenseHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
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

    public List<LicenseHolder> findAllLicenseHolders() {
        List<LicenseHolder> licenseHolders = (List<LicenseHolder>) licenseHolderRepository.findAll();
        return licenseHolders;
    }

    public List<LicenseHolder> findByIdentification(String identification) {
        return licenseHolderRepository.findByIdentification(identification);
    }
}
