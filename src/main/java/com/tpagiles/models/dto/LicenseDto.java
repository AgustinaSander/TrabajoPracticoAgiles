package com.tpagiles.models.dto;

import com.tpagiles.models.Address;
import com.tpagiles.models.EnumState;
import com.tpagiles.models.License;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LicenseDto {
    int id;
    String state;
    LicenseHolderDto licenseHolderDto;
    LicenseTypeDto licenseTypeDto;
    String comments;
    UserDto userDto;

    public LicenseDto(String state, LicenseHolderDto licenseHolderDto, LicenseTypeDto licenseTypeDto, String comments, UserDto userDto){
        this.state = state;
        this.licenseHolderDto = licenseHolderDto;
        this.licenseTypeDto = licenseTypeDto;
        this.comments = comments;
        this.userDto = userDto;
    }

    public License convertLicenseObject() {
        return new License(id, EnumState.valueOf(state), licenseHolderDto.convertLicenseHolderObject(), licenseTypeDto.convertLicenseTypeObject(),comments, userDto.convertUserObject());
    }
}
