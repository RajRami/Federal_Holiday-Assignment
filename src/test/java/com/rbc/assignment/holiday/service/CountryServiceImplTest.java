package com.rbc.assignment.holiday.service;

import com.rbc.assignment.holiday.entity.Country;
import com.rbc.assignment.holiday.exception.ResourceNotFoundException;
import com.rbc.assignment.holiday.repository.CountryRepository;
import com.rbc.assignment.holiday.service.impl.CountryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;


public class CountryServiceImplTest {
    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private CountryServiceImpl countryService;

    private Country country;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        country = new Country();
        country.setCode("CAN");
        country.setName("Canada");
    }

    @Test
    public void testAddCountry() {
        when(countryRepository.save(any(Country.class))).thenReturn(country);

        Country createdCountry = countryService.addCountry(country);

        assertNotNull(createdCountry);
        assertEquals("Canada", createdCountry.getName());
        assertEquals("CAN", createdCountry.getCode());
    }

    @Test
    public void testGetAllCountries() {
        when(countryRepository.findAll()).thenReturn(Arrays.asList(country));

        List<Country> countries = countryService.getAllCountries();

        assertNotNull(countries);
        assertEquals(1, countries.size());
        assertEquals("Canada", countries.get(0).getName());
    }

    @Test
    public void testGetCountryByCode() {
        when(countryRepository.findByCode("CAN")).thenReturn(Optional.of(country));

        Country foundCountry = countryService.getCountryByCode("CAN");

        assertNotNull(foundCountry);
        assertEquals("Canada", foundCountry.getName());
    }

    @Test
    public void testGetCountryByCodeNotFound() {
        when(countryRepository.findByCode("CAN")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> countryService.getCountryByCode("CAN"));
    }

    @Test
    public void testGetCountryById() {
        when(countryRepository.findById(anyInt())).thenReturn(Optional.of(country));

        Country foundCountry = countryService.getCountryById(1);

        assertNotNull(foundCountry);
        assertEquals("Canada", foundCountry.getName());
    }

    @Test
    public void testGetCountryByIdNotFound() {
        when(countryRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> countryService.getCountryById(1));
    }

    @Test
    public void testUpdateCountry() {
        when(countryRepository.findById(anyInt())).thenReturn(Optional.of(country));
        when(countryRepository.save(any(Country.class))).thenReturn(country);

        Country newCountryDetails = new Country();
        newCountryDetails.setName("Canada Updated");
        newCountryDetails.setCode("CAN");

        Country updatedCountry = countryService.updateCountry(1, newCountryDetails);

        assertNotNull(updatedCountry);
        assertEquals("Canada Updated", updatedCountry.getName());
        assertEquals("CAN", updatedCountry.getCode());
    }

    @Test
    public void testDeleteCountry() {
        when(countryRepository.findById(anyInt())).thenReturn(Optional.of(country));
        doNothing().when(countryRepository).delete(any(Country.class));

        countryService.deleteCountry(1);

        verify(countryRepository, times(1)).delete(country);
    }
}
