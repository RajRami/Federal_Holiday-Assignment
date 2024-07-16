package com.rbc.assignment.holiday.repository;

import com.rbc.assignment.holiday.entity.Country;
import com.rbc.assignment.holiday.entity.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Integer> {
    List<Holiday> findByCountry(Country country);

    @Query("SELECT h FROM Holiday h WHERE h.country.code = :countryCode AND MONTH(h.date) = :month")
    List<Holiday> findByCountryCodeAndMonth(@Param("countryCode") String countryCode, @Param("month") int month);

}
