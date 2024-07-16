package com.rbc.assignment.holiday.controller;

import com.rbc.assignment.holiday.entity.Country;
import com.rbc.assignment.holiday.repository.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CountryControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CountryRepository countryRepository;

    private String baseUrl;

    @BeforeEach
    public void setUp() {
        baseUrl = "http://localhost:" + port + "/api/countries";
        countryRepository.deleteAll();
    }

    @Test
    public void testGetAllCountries() {
        Country country1 = new Country(1, "United States", "US");
        Country country2 = new Country(2, "Canada", "CA");
        countryRepository.saveAll(List.of(country1, country2));

        ResponseEntity<Country[]> responseEntity = restTemplate.getForEntity(baseUrl, Country[].class);
        Country[] countries = responseEntity.getBody();

        assertThat(countries).hasSize(2);
        assertThat(countries).extracting("name").contains("United States", "Canada");
    }

    @Test
    public void testGetCountryByCode() {
        Country country = new Country(1, "United States", "US");
        countryRepository.save(country);

        ResponseEntity<Country> responseEntity = restTemplate.getForEntity(baseUrl + "/code/US", Country.class);
        Country fetchedCountry = responseEntity.getBody();

        assertThat(fetchedCountry).isNotNull();
        assertThat(fetchedCountry.getName()).isEqualTo("United States");
    }

    @Test
    public void testAddCountry() {
        Country country = new Country();
        country.setName("Canada");
        country.setCode("CA");

        ResponseEntity<Country> responseEntity = restTemplate.postForEntity("/api/countries", country, Country.class);
        Country createdCountry = responseEntity.getBody();

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(createdCountry).isNotNull();
        assertThat(createdCountry.getId()).isGreaterThan(0);
        assertThat(createdCountry.getName()).isEqualTo("Canada");
    }


    @Test
    public void testDeleteCountry() {
        Country country = new Country(1, "United States", "US");
        countryRepository.save(country);

        restTemplate.delete(baseUrl + "/1");

        Country countryInDb = countryRepository.findById(1).orElse(null);
        assertThat(countryInDb).isNull();
    }
}

