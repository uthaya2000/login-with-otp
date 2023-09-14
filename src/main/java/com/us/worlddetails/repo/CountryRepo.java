package com.us.worlddetails.repo;

import com.us.worlddetails.dto.CountryCodeDTO;
import com.us.worlddetails.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CountryRepo extends JpaRepository<Country, String> {

    @Query(value = "select NEW com.us.worlddetails.dto.CountryCodeDTO(c.name, c.code) from Country c where c.name LIKE %:search%")
    List<CountryCodeDTO> findMatchingCountry(@Param("search") String searchString);

    @Query(value = "SELECT * FROM country c WHERE c.code = :countryCode", nativeQuery = true)
    Country findCountryByCode(@Param("countryCode") String countryCode);
}
