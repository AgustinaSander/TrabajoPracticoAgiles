package com.tpagiles.repositories;

import com.tpagiles.models.LicenseType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LicenseTypeRepository extends CrudRepository<LicenseType, Integer> {
}
