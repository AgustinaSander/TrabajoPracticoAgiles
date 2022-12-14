package com.tpagiles.models.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LicenseFilter {
    private String name;
    private String surname;
    private String typeBlood;
    private String typeRh;
    private Boolean donor;
}
