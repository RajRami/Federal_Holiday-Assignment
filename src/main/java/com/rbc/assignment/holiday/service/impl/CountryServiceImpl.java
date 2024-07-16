package com.rbc.assignment.holiday.service.impl;

import com.rbc.assignment.holiday.entity.Country;
import com.rbc.assignment.holiday.exception.ResourceNotFoundException;
import com.rbc.assignment.holiday.repository.CountryRepository;
import com.rbc.assignment.holiday.service.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {
    private Logger logger = LoggerFactory.getLogger(CountryServiceImpl.class);
    private final CountryRepository countryRepository;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public Country addCountry(Country country) {
        country.setCode(country.getCode().toUpperCase());
        logger.info("Adding new country");
        return countryRepository.save(country);
    }

    @Override
    public List<Country> getAllCountries() {
        logger.info("Fetching all countries");
        return countryRepository.findAll();
    }

    @Override
    public Country getCountryByCode(String code) {
        logger.info("Getting country by code: " + code);
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
        logger.info("Updating country details : Id " + id);
        country.setName(countryDetails.getName());
        country.setCode(countryDetails.getCode());

        return countryRepository.save(country);
    }


    @Override
    public void deleteCountry(int id) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found for this id :: " + id));
        logger.info("Deleting country : id " + id);
        countryRepository.delete(country);
    }
}
