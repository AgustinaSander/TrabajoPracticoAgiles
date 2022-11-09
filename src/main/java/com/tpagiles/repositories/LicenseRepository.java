package com.tpagiles.repositories;

import com.tpagiles.models.License;
import com.tpagiles.models.LicenseType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LicenseRepository extends CrudRepository<License, Integer>{

}
