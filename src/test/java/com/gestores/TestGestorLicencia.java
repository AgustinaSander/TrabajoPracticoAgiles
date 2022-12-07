package com.gestores;

import com.tpagiles.dao.LicenseDAOImpl;
import com.tpagiles.dao.LicenseTypeDAOImpl;
import com.tpagiles.gestores.GestorLicencia;
import com.tpagiles.gestores.GestorTitular;
import com.tpagiles.gestores.GestorUser;
import com.tpagiles.models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestGestorLicencia {
    @Mock
    LicenseDAOImpl licenseDAO;
    @Mock
    LicenseTypeDAOImpl licenseTypeDAO;
    @Mock
    GestorUser gestorUser;
    @Mock
    GestorTitular gestorTitular;
    @InjectMocks
    GestorLicencia gestorLicencia;
    /*
    public License emitLicense(int idLicenseHolder, int idUser, String idTypeLicense, String comments) throws Exception {
        User user = gestorUser.findById(idUser);
        LicenseHolder licenseHolder = gestorTitular.findById(idLicenseHolder);
        LicenseType licenseType = findLicenseTypeById(idTypeLicense);
        LocalDate expirationDate = generateExpirationDate(licenseHolder);
        License license = new License(EnumState.VIGENTE, licenseHolder, licenseType, comments, user, LocalDate.now(), expirationDate);
        return licenseDAO.createLicense(license);
    }*/

    @Test
    @DisplayName("Should emit license")
    public void emitLicense() throws Exception {
        User user = mock(User.class);
        user.setId(1);
        when(gestorUser.findById(ArgumentMatchers.anyInt())).thenReturn(user);
        LicenseHolder licenseHolder = mock(LicenseHolder.class);
        licenseHolder.setId(1);
        when(gestorTitular.findById(ArgumentMatchers.anyInt())).thenReturn(licenseHolder);
        LicenseType licenseType = mock(LicenseType.class);
        licenseType.setId(1);
        when(licenseTypeDAO.findLicenseTypeById(ArgumentMatchers.any())).thenReturn(licenseType);
        LocalDate expirationDate = LocalDate.now();
        when(gestorLicencia.generateExpirationDate(ArgumentMatchers.any())).thenReturn(expirationDate);
        String comments = "Comments in license";
        License expectedLicense = new License(EnumState.VIGENTE, licenseHolder, licenseType, comments, user, LocalDate.now(), expirationDate);
        License actualLicense = gestorLicencia.emitLicense(1,1,"1",comments);
        assertThat(expectedLicense).isEqualTo(actualLicense);
    }

}
