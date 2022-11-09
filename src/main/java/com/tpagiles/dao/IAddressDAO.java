package com.tpagiles.dao;

import com.tpagiles.models.Address;

public interface IAddressDAO {
    public Address createAddress(Address address);
    public Address findById(int id);
}
