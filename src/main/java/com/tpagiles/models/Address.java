package com.tpagiles.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String street;
    String number;
    String floor;
    String department;
    String locality;
    String province;

    public Address(String street, String number, String floor, String department, String locality, String province){
        this.street = street;
        this.number = number;
        this.floor = floor;
        this.department = department;
        this.locality = locality;
        this.province = province;
    }

}
