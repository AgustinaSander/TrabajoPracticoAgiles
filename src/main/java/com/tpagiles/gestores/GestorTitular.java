package com.tpagiles.gestores;

import com.tpagiles.dao.AddressDAOImpl;
import com.tpagiles.dao.LicenseDAOImpl;
import com.tpagiles.dao.LicenseHolderDAOImpl;
import com.tpagiles.models.*;

import com.tpagiles.models.dto.AddressDto;
import com.tpagiles.models.dto.LicenseHolderDto;
import com.tpagiles.models.dto.PersonFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EnumType;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GestorTitular {
    @Autowired
    LicenseHolderDAOImpl licenseHolderDAO;
    @Autowired
    AddressDAOImpl addressDAO;
    @Autowired
    LicenseDAOImpl licenseDAO;

    public LicenseHolder createLicenseHolder(LicenseHolderDto licenseHolderDto) throws Exception {
        String identification = licenseHolderDto.getIdentification();
        List<LicenseHolder> licenseHolders = licenseHolderDAO.findByIdentification(identification);
        List<LicenseHolder> existentLicenseHolder = licenseHolders.stream().filter(u -> u.getType() == EnumTypeIdentification.valueOf(licenseHolderDto.getType()))
                .collect(Collectors.toList());

        if(existentLicenseHolder.size() == 0) {
            LicenseHolder licenseHolder = licenseHolderDto.convertLicenseHolderObject();
            //Create address first
            Address address = createAddress(licenseHolderDto.getAddressDto());
            licenseHolder.setAddress(address);
            //Create licenseholder
            return licenseHolderDAO.createLicenseHolder(licenseHolder);
        }
        throw new Exception("Ya existe un titular con ese tipo y numero de identificacion.");
    }

    public LicenseHolder updateLicenseHolder(int id, LicenseHolderDto licenseHolderDto) throws Exception {
        //Get license holder
        LicenseHolder licenseHolder = licenseHolderDAO.findById(id);
        if(licenseHolder==null)
            throw new Exception("The license holder with id " + id + " no longer exists.");

        String identification = licenseHolderDto.getIdentification();
        List<LicenseHolder> licenseHolders = licenseHolderDAO.findByIdentification(identification);
        List<LicenseHolder> existentLicenseHolder = licenseHolders.stream().filter(l -> l.getId() != id && l.getType() == EnumTypeIdentification.valueOf(licenseHolderDto.getType()))
                .collect(Collectors.toList());

        if(existentLicenseHolder.size() == 0) {
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
        throw new Exception("Ya existe un titular con ese tipo y numero de identificacion.");
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


    public List<LicenseHolder> findAllowLicenseHoldersByLicenseType(String type){
        int minAge = licenseDAO.getMinAgeByType(type);
        return getLicenseHoldersOlderThan(minAge);
    }

    public List<LicenseHolder> findAll(){
        return licenseHolderDAO.findAllLicenseHolders();
    }

    public List<LicenseHolder> getLicenseHoldersOlderThan(int age){
        List<LicenseHolder> holders = findAll();
        List<LicenseHolder> olders = holders.stream().filter(h -> h.getAge() >= age).collect(Collectors.toList());
        return olders;
    }

    public List<LicenseHolder> findAllWithFilters(PersonFilter filters) {
        List<LicenseHolder> licenseHolders = findAll();
        if(filters.getIdentification() != null){
            licenseHolders = licenseHolders.stream().filter(u -> u.getIdentification().equals(filters.getIdentification()))
                    .collect(Collectors.toList());
        }
        if(filters.getType() != null){
            licenseHolders = licenseHolders.stream().filter(u -> u.getType().name().equals(filters.getType()))
                    .collect(Collectors.toList());
        }
        if(filters.getName() != null){
            licenseHolders = licenseHolders.stream().filter(u -> u.getName().equals(filters.getName()))
                    .collect(Collectors.toList());
        }
        if(filters.getSurname() != null){
            licenseHolders = licenseHolders.stream().filter(u -> u.getSurname().equals(filters.getSurname()))
                    .collect(Collectors.toList());
        }
        return licenseHolders;
    }
}
