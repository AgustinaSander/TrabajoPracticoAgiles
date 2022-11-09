package com.tpagiles.gestores;

import com.tpagiles.dao.AddressDAOImpl;
import com.tpagiles.dao.LicenseHolderDAOImpl;
import com.tpagiles.models.*;

import com.tpagiles.models.dto.AddressDto;
import com.tpagiles.models.dto.LicenseHolderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EnumType;

@Service
public class GestorTitular {
    @Autowired
    LicenseHolderDAOImpl licenseHolderDAO;
    @Autowired
    AddressDAOImpl addressDAO;

    public LicenseHolder createLicenseHolder(LicenseHolderDto licenseHolderDto){
        LicenseHolder licenseHolder = licenseHolderDto.convertLicenseHolderObject();
        //Create address first
        Address address = createAddress(licenseHolderDto.getAddressDto());
        licenseHolder.setAddress(address);
        //Create licenseholder
        return licenseHolderDAO.createLicenseHolder(licenseHolder);
    }

    public LicenseHolder updateLicenseHolder(int id, LicenseHolderDto licenseHolderDto) throws Exception {
        //Get license holder
        LicenseHolder licenseHolder = licenseHolderDAO.findById(id);
        if(licenseHolder==null)
            throw new Exception("The license holder with id " + id + " no longer exists.");

        AddressDto addressDto = licenseHolderDto.getAddressDto();
        Address updatedAddress = updateAddress(addressDto.getId(), addressDto);

        licenseHolder.setName(licenseHolderDto.getName());
        licenseHolder.setSurname(licenseHolderDto.getSurname());
        licenseHolder.setEmail(licenseHolderDto.getEmail());
        licenseHolder.setOrganDonor(licenseHolderDto.isOrganDonor());
        licenseHolder.setBirthDate(licenseHolderDto.getBirthDate());
        licenseHolder.setType(EnumTypeIdentification.valueOf(licenseHolderDto.getType()));
        licenseHolder.setIdentification(licenseHolderDto.getIdentification());
        licenseHolder.setAddress(updatedAddress);
        licenseHolder.setBloodType(EnumBloodType.valueOf(licenseHolderDto.getBloodType()));
        licenseHolder.setRhFactor(EnumRHFactor.valueOf(licenseHolderDto.getRhFactor()));
        return licenseHolderDAO.createLicenseHolder(licenseHolder);
    }

    private Address updateAddress(int idAddress, AddressDto addressDto) throws Exception {
        Address address = addressDAO.findById(idAddress);
        if(address==null)
            throw new Exception("The address with id " + idAddress + " no longer exists.");

        address.setDepartment(addressDto.getDepartment());
        address.setFloor(addressDto.getFloor());
        address.setLocality(addressDto.getLocality());
        address.setNumber(addressDto.getNumber());
        address.setProvince(addressDto.getProvince());
        address.setStreet(addressDto.getStreet());

        return addressDAO.createAddress(address);
    }

    public Address createAddress(AddressDto addressDto){
        Address address = addressDto.convertAddressObject();
        return addressDAO.createAddress(address);
    }

    public LicenseHolder findById(int id){
        return licenseHolderDAO.findById(id);
    }
}
