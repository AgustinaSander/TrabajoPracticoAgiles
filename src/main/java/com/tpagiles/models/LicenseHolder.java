package com.tpagiles.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LicenseHolder extends Person{
    private LocalDate birthDate;
    @OneToOne
    private Address address;
    @Enumerated(EnumType.STRING)
    private EnumBloodType bloodType;
    @Enumerated(EnumType.STRING)
    private EnumRHFactor rhFactor;
    private boolean isOrganDonor;

    public LicenseHolder(String name, String surname, String email, EnumTypeIdentification type, String identification, LocalDate birthDate, Address address, EnumBloodType bloodType, EnumRHFactor rhFactor, boolean isOrganDonor){
        super(name, surname, email, type, identification);
        this.birthDate = birthDate;
        this.address = address;
        this.bloodType = bloodType;
        this.rhFactor = rhFactor;
        this.isOrganDonor = isOrganDonor;
    }

    public int getAge() {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
