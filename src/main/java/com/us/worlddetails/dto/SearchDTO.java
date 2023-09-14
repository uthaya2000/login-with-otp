package com.us.worlddetails.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchDTO {
    private List<CountryCodeDTO> countryNames;
    private List<String> cityNames;
    private List<String> languages;
}
