package com.us.worlddetails.repo;

import com.us.worlddetails.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepo extends JpaRepository<City, String> {
    @Query(value = "select DISTINCT c.Name from city c where c.Name LIKE %:search%",
            nativeQuery = true)
    List<String> findMatchingCity(@Param("search") String searchString);

    @Query(value = "SELECT * from city c WHERE c.CountryCode= :code", nativeQuery = true)
    List<City> findCityByCode(@Param("code") String code);
}
