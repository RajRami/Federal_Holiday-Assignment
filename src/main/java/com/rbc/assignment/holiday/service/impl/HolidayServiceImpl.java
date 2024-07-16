package com.rbc.assignment.holiday.service.impl;

import com.rbc.assignment.holiday.entity.Country;
import com.rbc.assignment.holiday.entity.Holiday;
import com.rbc.assignment.holiday.exception.ResourceNotFoundException;
import com.rbc.assignment.holiday.repository.CountryRepository;
import com.rbc.assignment.holiday.repository.HolidayRepository;
import com.rbc.assignment.holiday.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HolidayServiceImpl implements HolidayService {
    private final HolidayRepository holidayRepository;
    private final CountryRepository countryRepository;

    @Autowired
    public HolidayServiceImpl(HolidayRepository holidayRepository, CountryRepository countryRepository) {
        this.holidayRepository = holidayRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public Holiday addHoliday(Holiday holiday) {
        return holidayRepository.save(holiday);
    }
    @Override
    public void addHolidays(List<Holiday> holidays) {
        holidayRepository.saveAll(holidays);
    }

    @Override
    public Country getCountryByCode(String code) {
        return countryRepository.findByCode(code.toUpperCase())
                .orElseThrow(() -> new ResourceNotFoundException("Country not found by code :: " + code));
    }

    @Override
    public Holiday updateHoliday(int id, Holiday holidayDetails) {
        Holiday holiday = holidayRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Holiday not found for this id :: " + id));

        holiday.setName(holidayDetails.getName());
        holiday.setDate(holidayDetails.getDate());
        holiday.setCountry(holidayDetails.getCountry());

        return holidayRepository.save(holiday);
    }

    @Override
    public void deleteHoliday(int id) {
        Holiday holiday = holidayRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Holiday not found for this id :: " + id));
        holidayRepository.delete(holiday);
    }

    @Override
    public List<Holiday> getAllHolidays() {
        return holidayRepository.findAll();
    }


    @Override
    public Holiday getHolidayById(int id) {
        return holidayRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Holiday not found for this id :: " + id));
    }

    @Override
    public List<Holiday> listHolidaysByCountry(String countryCode) {
        Country country = countryRepository.findByCode(countryCode.toUpperCase())
                .orElseThrow(() -> new ResourceNotFoundException("Country not found by code :: " + countryCode));

        return holidayRepository.findByCountry(country);
    }

    @Override
    public List<Holiday> listHolidaysByCountryAndMonth(String countryCode, int month) {
        Country country = countryRepository.findByCode(countryCode.toUpperCase())
                .orElseThrow(() -> new ResourceNotFoundException("Country not found"));

        return holidayRepository.findByCountryCodeAndMonth(country.getCode(), month);
    }
}
