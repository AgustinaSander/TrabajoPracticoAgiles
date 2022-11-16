package com.tpagiles.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LicenseInfo {
    private int idUser;
    private int idLicenseHolder;
    private String nameTypeLicense;
    private String comments;
}
