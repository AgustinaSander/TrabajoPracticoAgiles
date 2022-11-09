package com.tpagiles.models;

import lombok.*;

import javax.persistence.*;

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
    int id;
    @Enumerated(EnumType.STRING)
    EnumState state;
    @ManyToOne
    LicenseHolder licenseHolder;
    @ManyToOne
    LicenseType type;
    String comments;
    @ManyToOne
    User user;

    public License(EnumState state, LicenseHolder licenseHolder, LicenseType type, String comments, User user){
        this.state = state;
        this.licenseHolder = licenseHolder;
        this.type = type;
        this.comments = comments;
        this.user = user;
    }
}
