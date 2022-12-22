package com.tpagiles.dao;

import com.tpagiles.models.License;
import com.tpagiles.repositories.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LicenseDAOImpl implements ILicenseDAO{
    @Autowired
    LicenseRepository licenseRepository;

    @Override
    public License createLicense(License license) {
        return licenseRepository.save(license);
    }

    @Override
    public List<License> findLicensesByTypeByHolderId(int idLicenseHolder, int idLicenseType) {
        return licenseRepository.findLicensesByTypeByHolderId(idLicenseHolder, idLicenseType);
    }

    @Override
    public List<License> findLicensesByHolderId(int idLicenseHolder) {
        return licenseRepository.findByHolderId(idLicenseHolder);
    }

    @Override
    public List<License> findAllCurrentLicenses() {
        return licenseRepository.findAllCurrentLicenses();
    }

    @Override
    public License updateLicense(int id) {
        licenseRepository.updateLicense(id);
        return licenseRepository.findById(id).get();
    }

    @Override
    public List<License> findCurrentLicensesByHolderId(int idLicenseHolder) {
        return licenseRepository.findCurrentByHolderId(idLicenseHolder);
    }

    @Override
    public List<License> findAll() { return (List<License>) licenseRepository.findAll(); }

    @Override
    public List<License> findAllExpiredLicense() {
        return licenseRepository.findAllExpiredLicenses();
    }
}
