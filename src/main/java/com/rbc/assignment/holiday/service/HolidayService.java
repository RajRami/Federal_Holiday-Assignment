package com.rbc.assignment.holiday.service;

import com.rbc.assignment.holiday.entity.Country;
import com.rbc.assignment.holiday.entity.Holiday;

import java.util.List;

public interface HolidayService {
    Holiday addHoliday(Holiday holiday);

    Holiday updateHoliday(int id, Holiday holidayDetails);

    List<Holiday> getAllHolidays();
    void addHolidays(List<Holiday> holidays);

    Holiday getHolidayById(int id);

    List<Holiday> listHolidaysByCountry(String countryCode);

    List<Holiday> listHolidaysByCountryAndMonth(String countryCode, int month);

    void deleteHoliday(int id);

    Country getCountryByCode(String countryCode);
}
