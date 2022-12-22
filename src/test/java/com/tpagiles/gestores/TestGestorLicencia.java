package com.tpagiles.gestores;

import com.tpagiles.models.EnumState;
import com.tpagiles.models.License;
import com.tpagiles.models.LicenseHolder;
import com.tpagiles.models.LicenseType;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import java.util.stream.Collectors;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestGestorLicencia {
    @Autowired
    public GestorLicencia gestorLicencia;

    @Test
    @DisplayName("Should return expiration date for 1 year")
    public void testGenerateExpirationDateFor1Year(){
        LicenseHolder licenseHolder = Mockito.mock(LicenseHolder.class);
        licenseHolder.setBirthDate(LocalDate.of(2002,04,24));
        doReturn(LocalDate.of(2002,04,24)).when(licenseHolder).getBirthDate();
        doReturn(20).when(licenseHolder).getAge();
        LocalDate expectedDate = LocalDate.of(LocalDate.now().getYear()+1, 04, 24);
        LocalDate actualDate = gestorLicencia.generateExpirationDate(licenseHolder);
        assertThat(actualDate).isEqualTo(expectedDate);
    }

    @Test
    @DisplayName("Should return expiration date for 5 years")
    public void testGenerateExpirationDateFor5Years(){
        LicenseHolder licenseHolder = Mockito.mock(LicenseHolder.class);
        licenseHolder.setBirthDate(LocalDate.of(2000,04,24));
        doReturn(LocalDate.of(2000,04,24)).when(licenseHolder).getBirthDate();
        doReturn(22).when(licenseHolder).getAge();
        LocalDate expectedDate = LocalDate.of(LocalDate.now().getYear()+5, 04, 24);
        LocalDate actualDate = gestorLicencia.generateExpirationDate(licenseHolder);
        assertThat(actualDate).isEqualTo(expectedDate);
    }

    @Test
    @DisplayName("Should calculate license cost for 3 years")
    public void testCalculateLicenseCostFor3Years(){
        LicenseType licenseType = new LicenseType("A","Ciclomotores, motocicletas y triciclos motorizados.",17, 20.0,25.0,30.0,40.0);
        int yearPermission = 3;
        double expectedCost = licenseType.getPrice2() + 8;
        double actualCost = gestorLicencia.calculateLicenseCost(yearPermission, licenseType);
        assertThat(actualCost).isEqualTo(expectedCost);
    }

    @Test
    @DisplayName("Should calculate license cost for 5 years")
    public void testCalculateLicenseCostFor5Years(){
        LicenseType licenseType = new LicenseType("A","Ciclomotores, motocicletas y triciclos motorizados.",17, 20.0,25.0,30.0,40.0);
        int yearPermission = 5;
        double expectedCost = licenseType.getPrice4() + 8;
        double actualCost = gestorLicencia.calculateLicenseCost(yearPermission, licenseType);
        assertThat(actualCost).isEqualTo(expectedCost);
    }
}

