package com.rbc.assignment.holiday.repository;

import com.rbc.assignment.holiday.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
    Optional<Country> findByCode(String countryCode);
}
