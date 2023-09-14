package com.us.worlddetails.repo;

import com.us.worlddetails.entity.CountryLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LanguageRepo extends JpaRepository<CountryLanguage, String> {
    @Query(value = "select DISTINCT c.language from countrylanguage c where c.language LIKE %:search%",
            nativeQuery = true)
    List<String> findMatchingLanguage(@Param("search") String searchString);

    @Query(value = "SELECT * from countrylanguage l WHERE l.countryCode=:code", nativeQuery = true)
    List<CountryLanguage> findLangByCode(@Param("code") String code);
}
