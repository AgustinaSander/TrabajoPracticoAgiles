package com.tpagiles.repositories;

import com.tpagiles.models.Address;
import com.tpagiles.models.LicenseType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<Address, Integer> {
}
