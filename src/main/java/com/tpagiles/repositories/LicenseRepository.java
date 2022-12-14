package com.tpagiles.repositories;

import com.tpagiles.models.License;
import com.tpagiles.models.LicenseType;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface LicenseRepository extends CrudRepository<License, Integer>{

    @Query("SELECT l FROM License l WHERE l.licenseHolder.id = :idLicenseHolder AND l.type.id = :idLicenseType")
    List<License> findLicensesByTypeByHolderId(int idLicenseHolder, int idLicenseType);

    @Query("SELECT l FROM License l WHERE l.licenseHolder.id = :idLicenseHolder")
    List<License> findByHolderId(int idLicenseHolder);

    @Query("SELECT l FROM License l WHERE l.state = 'VIGENTE'")
    List<License> findAllCurrentLicenses();

    @Query("SELECT l FROM License l WHERE l.licenseHolder.id = :idLicenseHolder AND l.state = 'VIGENTE'")
    List<License> findCurrentByHolderId(int idLicenseHolder);

    @Modifying
    @Query("UPDATE License l SET l.state = 'EXPIRADA' WHERE l.id = :idLicense")
    void updateLicense(int idLicense);

    @Query("SELECT * FROM `license` WHERE 1;")
    List<License> findAll();
}
