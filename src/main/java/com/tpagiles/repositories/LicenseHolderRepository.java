package com.tpagiles.repositories;

import com.tpagiles.models.LicenseHolder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicenseHolderRepository extends CrudRepository<LicenseHolder, Integer> {
}
