package com.tpagiles.dao;

import com.tpagiles.models.LicenseType;

import java.util.List;

public abstract class ILicenseTypeDAO {
    public abstract LicenseType findLicenseTypeById(int id);

    public abstract int getMinAgeByType(String type);

    public abstract List<LicenseType> findAllTypes();

    public abstract LicenseType findLicenseTypeByName(String name);
}
