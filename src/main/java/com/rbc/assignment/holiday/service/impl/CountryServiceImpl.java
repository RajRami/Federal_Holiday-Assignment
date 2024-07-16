package com.rbc.assignment.holiday.service.impl;

import com.rbc.assignment.holiday.entity.Country;
import com.rbc.assignment.holiday.exception.ResourceNotFoundException;
import com.rbc.assignment.holiday.repository.CountryRepository;
import com.rbc.assignment.holiday.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public Country addCountry(Country country) {
        country.setCode(country.getCode().toUpperCase());
        return countryRepository.save(country);
    }

    @Override
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    public Country getCountryByCode(String code) {
        return countryRepository.findByCode(code.toUpperCase())
                .orElseThrow(() -> new ResourceNotFoundException("Country not found by code :: " + code));
    }

    @Override
    public Country getCountryById(int id) {
        return countryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found for this id :: " + id));
    }

    @Override
    public Country updateCountry(int id, Country countryDetails) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found for this id :: " + id));

        country.setName(countryDetails.getName());
        country.setCode(countryDetails.getCode());

        return countryRepository.save(country);
    }


    @Override
    public void deleteCountry(int id) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found for this id :: " + id));
        countryRepository.delete(country);
    }
}
