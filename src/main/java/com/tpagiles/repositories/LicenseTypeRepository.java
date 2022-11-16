package com.tpagiles.repositories;

import com.tpagiles.models.LicenseType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LicenseTypeRepository extends CrudRepository<LicenseType, Integer> {

    @Query("SELECT l FROM LicenseType l WHERE l.name= :name")
    Optional<LicenseType> findByName(String name);
}
