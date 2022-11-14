package com.tpagiles.models.dto;

import com.tpagiles.models.*;
import lombok.*;
import org.apache.tomcat.jni.Local;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LicenseHolderDto {
    private int id;
    private String name;
    private String surname;
    private String email;
    private String type;
    private String identification;
    private LocalDate birthDate;
    private AddressDto addressDto;
    private String bloodType;
    private String rhFactor;
    private boolean isOrganDonor;

    public LicenseHolderDto(String name, String surname, String email, String type, String identification, LocalDate birthDate, AddressDto address, String bloodType, String rhFactor, boolean isOrganDonor){
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.type = type;
        this.identification = identification;
        this.birthDate = birthDate;
        this.addressDto = address;
        this.bloodType = bloodType;
        this.rhFactor = rhFactor;
        this.isOrganDonor = isOrganDonor;
    }

    public LicenseHolder convertLicenseHolderObject() {
        EnumTypeIdentification typeEnum = EnumTypeIdentification.valueOf(type);
        EnumBloodType bloodTypeEnum = EnumBloodType.valueOf(bloodType);
        EnumRHFactor rhFactorEnum = EnumRHFactor.valueOf(rhFactor);
        Address address = addressDto!= null ? addressDto.convertAddressObject() : null;

        return new LicenseHolder(name, surname, email, typeEnum, identification, birthDate, address, bloodTypeEnum, rhFactorEnum, isOrganDonor);
    }
}
