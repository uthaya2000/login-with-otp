package com.us.worlddetails.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "countrylanguage")
public class CountryLanguage {

    @EmbeddedId
    private CountryLanguageId id;

    @Column(name = "IsOfficial")
    private Boolean isOfficial;

    @Column(name = "Percentage")
    private Float percentage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CountryCode", referencedColumnName = "Code", insertable = false, updatable = false)
    private Country country;

    @Embeddable
    @Getter
    @Setter
    public static class CountryLanguageId implements Serializable {
        @Column(name = "CountryCode")
        private String countryCode;

        @Column(name = "Language")
        private String language;
    }
}
