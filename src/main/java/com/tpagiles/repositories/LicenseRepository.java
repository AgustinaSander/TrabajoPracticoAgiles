package com.tpagiles.repositories;

import com.tpagiles.models.License;
import com.tpagiles.models.LicenseType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicenseRepository extends CrudRepository<License, Integer>{

    @Query("SELECT l FROM License l WHERE l.licenseHolder = :idLicenseHolder AND l.type = :idLicenseType")
    List<License> findLicensesByTypeByHolderId(int idLicenseHolder, int idLicenseType);

    @Query("SELECT l FROM License l WHERE l.licenseHolder =:idLicenseHolder")
    List<License> findByHolderId(int idLicenseHolder);
}
