package com.rbc.assignment.holiday.service;

import com.rbc.assignment.holiday.entity.Country;
import com.rbc.assignment.holiday.entity.Holiday;
import com.rbc.assignment.holiday.exception.ResourceNotFoundException;
import com.rbc.assignment.holiday.repository.CountryRepository;
import com.rbc.assignment.holiday.repository.HolidayRepository;
import com.rbc.assignment.holiday.service.impl.HolidayServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HolidayServiceImplTest {
    @Mock
    private HolidayRepository holidayRepository;

    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private HolidayServiceImpl holidayService;

    private Holiday holiday;
    private Country country;

    @BeforeEach
    public void setUp() {
        country = new Country();
        country.setCode("CAN");
        country.setName("Canada");

        holiday = new Holiday();
        holiday.setName("Canada Day");
        holiday.setDate(LocalDate.of(2024, 7, 1));
        holiday.setCountry(country);
    }

    @Test
    public void testAddHoliday() {
        when(holidayRepository.save(any(Holiday.class))).thenReturn(holiday);

        Holiday createdHoliday = holidayService.addHoliday(holiday);

        assertNotNull(createdHoliday);
        assertEquals("Canada Day", createdHoliday.getName());
        assertEquals(LocalDate.of(2024, 7, 1), createdHoliday.getDate());
        assertEquals("Canada", createdHoliday.getCountry().getName());
    }

    @Test
    public void testAddHolidays() {

        List<Holiday> holidays = new ArrayList<>();
        holidays.add(holiday);
        Holiday holiday2 = new Holiday();
        holiday2.setName("Victoria Day");
        holiday2.setDate(LocalDate.of(2024, 5, 20));
        holiday2.setCountry(country);
        holidays.add(holiday2);


        when(holidayRepository.saveAll(holidays)).thenReturn(holidays);

        holidayService.addHolidays(holidays);
        assertEquals(2, holidays.size());

        verify(holidayRepository, times(1)).saveAll(holidays);
    }

    @Test
    public void testGetCountryByCode() {
        when(countryRepository.findByCode("CAN")).thenReturn(Optional.of(country));

        Country foundCountry = holidayService.getCountryByCode("CAN");

        assertNotNull(foundCountry);
        assertEquals("Canada", foundCountry.getName());
    }

    @Test
    public void testGetCountryByCodeNotFound() {
        when(countryRepository.findByCode("CAN")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> holidayService.getCountryByCode("CAN"));
    }

    @Test
    public void testUpdateHoliday() {
        when(holidayRepository.findById(anyInt())).thenReturn(Optional.of(holiday));
        when(holidayRepository.save(any(Holiday.class))).thenReturn(holiday);

        Holiday newHolidayDetails = new Holiday();
        newHolidayDetails.setName("Updated Canada Day");
        newHolidayDetails.setDate(LocalDate.of(2024, 8, 1));
        newHolidayDetails.setCountry(country);

        Holiday updatedHoliday = holidayService.updateHoliday(1, newHolidayDetails);

        assertNotNull(updatedHoliday);
        assertEquals("Updated Canada Day", updatedHoliday.getName());
        assertEquals(LocalDate.of(2024, 8, 1), updatedHoliday.getDate());
        assertEquals("Canada", updatedHoliday.getCountry().getName());
    }

    @Test
    public void testDeleteHoliday() {
        when(holidayRepository.findById(anyInt())).thenReturn(Optional.of(holiday));
        doNothing().when(holidayRepository).delete(any(Holiday.class));

        holidayService.deleteHoliday(1);

        verify(holidayRepository, times(1)).delete(holiday);
    }

    @Test
    public void testGetAllHolidays() {
        when(holidayRepository.findAll()).thenReturn(Arrays.asList(holiday));

        List<Holiday> holidays = holidayService.getAllHolidays();

        assertNotNull(holidays);
        assertEquals(1, holidays.size());
        assertEquals("Canada Day", holidays.get(0).getName());
    }

    @Test
    public void testGetHolidayById() {
        when(holidayRepository.findById(anyInt())).thenReturn(Optional.of(holiday));

        Holiday foundHoliday = holidayService.getHolidayById(1);

        assertNotNull(foundHoliday);
        assertEquals("Canada Day", foundHoliday.getName());
    }

    @Test
    public void testListHolidaysByCountry() {
        when(countryRepository.findByCode("CAN")).thenReturn(Optional.of(country));
        when(holidayRepository.findByCountry(any(Country.class))).thenReturn(Arrays.asList(holiday));

        List<Holiday> holidays = holidayService.listHolidaysByCountry("CAN");

        assertNotNull(holidays);
        assertEquals(1, holidays.size());
        assertEquals("Canada Day", holidays.get(0).getName());
    }

    @Test
    public void testListHolidaysByCountryAndMonth() {
        when(countryRepository.findByCode("CAN")).thenReturn(Optional.of(country));
        when(holidayRepository.findByCountryCodeAndMonth("CAN", 7)).thenReturn(Arrays.asList(holiday));

        List<Holiday> holidays = holidayService.listHolidaysByCountryAndMonth("CAN", 7);

        assertNotNull(holidays);
        assertEquals(1, holidays.size());
        assertEquals("Canada Day", holidays.get(0).getName());
    }
}
