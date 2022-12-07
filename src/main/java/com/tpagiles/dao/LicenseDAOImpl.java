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
}
