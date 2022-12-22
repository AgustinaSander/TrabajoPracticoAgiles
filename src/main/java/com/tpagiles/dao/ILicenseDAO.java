package com.tpagiles.dao;

import com.tpagiles.models.License;

import java.util.List;

public interface ILicenseDAO {
    License createLicense(License license);

    List<License> findLicensesByTypeByHolderId(int idLicenseHolder, int idLicenseType);

    List<License> findLicensesByHolderId(int idLicenseHolder);

    List<License> findAllCurrentLicenses();

    License updateLicense(int id);

    List<License> findCurrentLicensesByHolderId(int idLicenseHolder);

    List<License> findAll();

    List<License> findAllExpiredLicense();
}

