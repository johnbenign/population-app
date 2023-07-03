package com.klasha.population.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class CityAndPopulationDto {
    private boolean error;
    private int status;
    private String msg;
    private List<Population> data;
}
