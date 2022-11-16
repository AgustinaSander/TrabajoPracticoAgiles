package com.tpagiles.dao;

import com.tpagiles.models.LicenseType;
import com.tpagiles.repositories.LicenseTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LicenseTypeDAOImpl extends ILicenseTypeDAO{
    @Autowired
    LicenseTypeRepository licenseTypeRepository;

    @Override
    public LicenseType findLicenseTypeById(int id){
        Optional<LicenseType> type = licenseTypeRepository.findById(id);
        return type.isPresent() ? type.get() : null;
    }

    @Override
    public int getMinAgeByType(String type) {
        List<LicenseType> types = findAllTypes();
        LicenseType licenseType = types.stream().filter(t -> t.getName().equals(type)).collect(Collectors.toList()).get(0);
        return licenseType.getMinAge();
    }

    @Override
    public List<LicenseType> findAllTypes(){
        return (List<LicenseType>) licenseTypeRepository.findAll();
    }

    @Override
    public LicenseType findLicenseTypeByName(String name) {
        Optional<LicenseType> type = licenseTypeRepository.findByName(name);
        return type.isPresent() ? type.get() : null;
    }
}
