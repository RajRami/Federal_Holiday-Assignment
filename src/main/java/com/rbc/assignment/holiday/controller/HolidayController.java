package com.rbc.assignment.holiday.controller;

import com.rbc.assignment.holiday.dto.HolidayRequest;
import com.rbc.assignment.holiday.entity.Country;
import com.rbc.assignment.holiday.entity.Holiday;
import com.rbc.assignment.holiday.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/holidays")
public class HolidayController {
    private final HolidayService holidayService;

    @Autowired
    public HolidayController(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    @GetMapping
    public ResponseEntity<List<Holiday>> getAllCountries() {
        List<Holiday> holidayList = holidayService.getAllHolidays();
        return ResponseEntity.ok(holidayList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Holiday> getHolidayById(@PathVariable int id) {
        return ResponseEntity.ok(holidayService.getHolidayById(id));
    }

    @GetMapping("/{countryCode}")
    public ResponseEntity<List<Holiday>> listHolidaysByCountry(@PathVariable String countryCode) {
        List<Holiday> holidays = holidayService.listHolidaysByCountry(countryCode);
        return ResponseEntity.ok(holidays);
    }

//    @GetMapping("/{countryCode}/month/{month}")
//    public ResponseEntity<List<Holiday>> listHolidaysByCountryAndMonth(@PathVariable String countryCode, @PathVariable int month) {
//        List<Holiday> holidays = holidayService.listHolidaysByCountryAndMonth(countryCode, month);
//        return ResponseEntity.ok(holidays);
//    }

    @GetMapping("/search")
    public ResponseEntity<List<Holiday>> listHolidaysByCountryAndMonth(@RequestParam String countryCode, @RequestParam int month) {
        List<Holiday> holidays = holidayService.listHolidaysByCountryAndMonth(countryCode, month);
        return ResponseEntity.ok(holidays);
    }

    @PostMapping("/addAll")
    public ResponseEntity<HttpStatus> addHolidays(@RequestBody List<HolidayRequest> holidayRequests){
        List<Holiday> holidays = new ArrayList<>();
        //Part of validator
        holidayRequests.forEach(data -> {
            Holiday holiday = new Holiday();
            holiday.setName(data.getName());
            holiday.setDate(data.getDate());
            Country country = holidayService.getCountryByCode(data.getCountryCode());
            holiday.setCountry(country);
            holidays.add(holiday);
        });
        holidayService.addHolidays(holidays);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addHoliday(@RequestBody HolidayRequest holidayRequest) {
        Holiday holiday = new Holiday();
        holiday.setName(holidayRequest.getName());
        holiday.setDate(holidayRequest.getDate());
        Country country = holidayService.getCountryByCode(holidayRequest.getCountryCode());
        holiday.setCountry(country);

        holidayService.addHoliday(holiday);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Holiday> updateHoliday(@PathVariable int id, @RequestBody HolidayRequest holidayRequest) {
        Holiday holidayDetails = new Holiday();
        holidayDetails.setName(holidayRequest.getName());
        holidayDetails.setDate(holidayRequest.getDate());
        holidayDetails.setCountry(holidayService.getCountryByCode(holidayRequest.getCountryCode()));

        Holiday updatedHoliday = holidayService.updateHoliday(id, holidayDetails);
        return ResponseEntity.ok(updatedHoliday);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHoliday(@PathVariable int id) {
        holidayService.deleteHoliday(id);
        return ResponseEntity.noContent().build();
    }
}
