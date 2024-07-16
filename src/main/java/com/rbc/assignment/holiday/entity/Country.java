package com.rbc.assignment.holiday.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id")
    private int id;

    @Column(name = "country_name", nullable = false, unique = true)
    private String name;

    @Column(name = "country_code", unique = true, nullable = false)
    private String code; // ISO country code (e.g., CA for Canada, US for USA)
}
