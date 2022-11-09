package com.tpagiles.models.dto;

import com.tpagiles.models.Address;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    int id;
    String street;
    String number;
    String floor;
    String department;
    String locality;
    String province;

    public AddressDto(String street, String number, String floor, String department, String locality, String province){
        this.street = street;
        this.number = number;
        this.floor = floor;
        this.department = department;
        this.locality = locality;
        this.province = province;
    }

    public Address convertAddressObject() {
        return new Address(id, street, number, floor, department, locality, province);
    }
}
