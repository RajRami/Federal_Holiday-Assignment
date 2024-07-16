package com.rbc.assignment.holiday.service;

import com.rbc.assignment.holiday.entity.Country;

import java.util.List;

public interface CountryService {
    Country addCountry(Country country);

    List<Country> getAllCountries();

    Country getCountryByCode(String code);

    Country getCountryById(int id);

    Country updateCountry(int id, Country countryDetails);

    void deleteCountry(int id);
}
