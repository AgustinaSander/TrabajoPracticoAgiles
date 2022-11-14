package com.tpagiles;

import com.tpagiles.controllers.UserController;
import com.tpagiles.dao.IUserDAO;
import com.tpagiles.dao.UserDAOImpl;
import com.tpagiles.gestores.GestorLicencia;
import com.tpagiles.gestores.GestorTitular;
import com.tpagiles.gestores.GestorUser;
import com.tpagiles.models.*;
import com.tpagiles.models.dto.AddressDto;
import com.tpagiles.models.dto.LicenseHolderDto;
import com.tpagiles.models.dto.UserDto;
import com.tpagiles.repositories.LicenseTypeRepository;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
@Import({ClassConfig.class})
public class TpAgiles {

  @Autowired
  GestorUser gestorUser;
  @Autowired
  GestorLicencia gestorLicencia;
  @Autowired
  GestorTitular gestorTitular;
  @Autowired
  LicenseTypeRepository licenseTypeRepository;

  @Component
  class DataSetup implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception{
/*
      //Ver si lo borro y lo pongo solo en la bd
      licenseTypeRepository.save(new LicenseType(1,"A","Ciclomotores, motocicletas y triciclos motorizados.",17, 20.0,25.0,30.0,40.0));
      licenseTypeRepository.save(new LicenseType(2,"B","Automóviles y camionetas con acoplado.",17, 20.0,25.0,30.0,40.0));
      licenseTypeRepository.save(new LicenseType(3,"C","Camiones sin acoplado y los comprendidos en la clase B.",21, 23.0,30.0,35.0,47.0));
      licenseTypeRepository.save(new LicenseType(4,"D","Servicio de transporte de pasajeros, emergencia, seguridad y los comprendidos en la clase B o C, según el caso.",21, 25.0,32.0,37.0,50.0));
      licenseTypeRepository.save(new LicenseType(5,"E","Camiones articulados o con acoplado, maquinaria especial no agrícola y los comprendidos en la clase B y C.",21, 29.0,39.0,44.0,59.0));
      licenseTypeRepository.save(new LicenseType(6,"F","Automotores especialmente adaptados para discapacitados.",17, 40.0,45.0,50.0,60.0));
      licenseTypeRepository.save(new LicenseType(7,"G","Tractores agrícolas y maquinaria especial agrícola.",17, 20.0,25.0,30.0,40.0));

      UserDto userDto = new UserDto("Agustina", "Sander", "asander00@hotmail.com", EnumTypeIdentification.DNI.name(), "42331387", "hola");
      User userCreated = gestorUser.createUser(userDto);

      AddressDto addressDto = new AddressDto("Castelli", "1621", "","", "Santa Fe", "Santa Fe");
      LicenseHolderDto licenseHolderDto = new LicenseHolderDto("Araceli", "Sarina", "arasarina@hotmail.com", EnumTypeIdentification.DNI.name(), "43221342", LocalDate.of(2000, 1, 18), addressDto, EnumBloodType.A.name(), EnumRHFactor.NEGATIVO.name(), true);
      LicenseHolder licenseHolderCreated = gestorTitular.createLicenseHolder(licenseHolderDto);
      Address addressCreated = licenseHolderCreated.getAddress();
      addressDto.setId(addressCreated.getId());
      addressDto.setStreet("nueva");

      gestorLicencia.emitLicense(licenseHolderCreated.getId(),userCreated.getId(),1);

      //Update licenseHolder
      licenseHolderDto.setId(licenseHolderCreated.getId());
      licenseHolderDto.setBirthDate(LocalDate.of(2022, 2,2));
      licenseHolderDto.setOrganDonor(false);
      licenseHolderDto.setAddressDto(addressDto);
      gestorTitular.updateLicenseHolder(licenseHolderCreated.getId(), licenseHolderDto);

      //Update user
      userDto.setEmail("nuevaomail@hotmail.com");
      gestorUser.updateUser(userCreated.getId(), userDto);
        gestorLicencia.emitLicense(22,23,2);


      gestorTitular.findAllowLicenseHoldersByLicenseType("A");
*/
  }}

  public static void main(String[] args) {
    SpringApplication.run(TpAgiles.class, args);
  }

}

