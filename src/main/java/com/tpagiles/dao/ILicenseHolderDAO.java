package com.tpagiles.dao;

import com.tpagiles.models.LicenseHolder;

public interface ILicenseHolderDAO {
    LicenseHolder createLicenseHolder(LicenseHolder licenseHolder);
    LicenseHolder findById(int id);
}
