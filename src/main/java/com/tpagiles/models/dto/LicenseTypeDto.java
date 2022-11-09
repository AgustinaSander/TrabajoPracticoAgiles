package com.tpagiles.models.dto;

import com.tpagiles.models.LicenseType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LicenseTypeDto {
    int id;
    String name;
    String allowVehicles;
    int minAge;
    double price1;
    double price2;
    double price3;
    double price4;

    public LicenseTypeDto(String name, String allowVehicles, int minAge, double price1,double price2,double price3,double price4){
        this.name = name;
        this.allowVehicles = allowVehicles;
        this.minAge = minAge;
        this.price1 = price1;
        this.price2 = price2;
        this.price3 = price3;
        this.price4 = price4;
    }
    public LicenseType convertLicenseTypeObject() {
        return new LicenseType(id, name, allowVehicles, minAge, price1, price2, price3, price4);
    }
}
