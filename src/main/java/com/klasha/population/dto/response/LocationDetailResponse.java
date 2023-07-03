package com.klasha.population.dto.response;

import lombok.Data;

@Data
public class LocationDetailResponse {
    private boolean error;
    private int status;
    private String msg;
    private LocationDetail data;
}
