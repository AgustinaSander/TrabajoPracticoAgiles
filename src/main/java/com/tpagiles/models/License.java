package com.tpagiles.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "license")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class License {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Enumerated(EnumType.STRING)
    private EnumState state;
    @ManyToOne
    private LicenseHolder licenseHolder;
    @ManyToOne
    private LicenseType type;
    private String comments;
    @ManyToOne
    private User user;
    private LocalDate expirationDate;

    public License(EnumState state, LicenseHolder licenseHolder, LicenseType type, String comments, User user, LocalDate expirationDate){
        this.state = state;
        this.licenseHolder = licenseHolder;
        this.type = type;
        this.comments = comments;
        this.user = user;
        this.expirationDate = expirationDate;
    }
}
