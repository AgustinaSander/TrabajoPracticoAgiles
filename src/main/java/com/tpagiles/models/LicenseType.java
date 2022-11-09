package com.tpagiles.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "license_type")
public class LicenseType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    String allowVehicles;
    int minAge;
    double price1;
    double price2;
    double price3;
    double price4;

    public LicenseType(String name, String allowVehicles, int minAge, double price1,double price2,double price3,double price4){
        this.name = name;
        this.allowVehicles = allowVehicles;
        this.minAge = minAge;
        this.price1 = price1;
        this.price2 = price2;
        this.price3 = price3;
        this.price4 = price4;
    }
}
