package com.us.worlddetails.dto;

import com.us.worlddetails.entity.City;
import com.us.worlddetails.entity.Country;
import com.us.worlddetails.entity.CountryLanguage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CountryDetailsDTO {
    private Country country;
    private List<City> cities;
    private List<CountryLanguage> countryLanguages;
}

