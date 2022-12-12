package com.tpagiles.dao;

import com.tpagiles.models.License;
import com.tpagiles.models.User;

import java.util.List;

public interface ILicenseDAO {
    License createLicense(License license);

    List<License> findLicensesByTypeByHolderId(int idLicenseHolder, int idLicenseType);

    List<License> findAllLicenses();
}
