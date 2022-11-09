package com.tpagiles.dao;

import com.tpagiles.models.Address;
import com.tpagiles.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressDAOImpl implements IAddressDAO{
    @Autowired
    AddressRepository addressRepository;

    @Override
    public Address createAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address findById(int id){
        Optional<Address> address = addressRepository.findById(id);
        return address.isPresent() ? address.get() : null;
    }
}
