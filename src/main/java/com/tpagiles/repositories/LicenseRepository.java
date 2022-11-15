package com.tpagiles.repositories;

import com.tpagiles.models.License;
import com.tpagiles.models.LicenseType;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicenseRepository extends CrudRepository<License, Integer>{

    @Query("SELECT l FROM License l WHERE l.license_holder_id := idLicenseHolder AND l.type_id := idLicenseType")
    List<License> findLicensesByTypeByHolderId(int idLicenseHolder, int idLicenseType);
}
