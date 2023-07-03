package com.klasha.population.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class StateDetailResponse {
    private boolean error;
    private int status;
    private String msg;
    private List<StateDetail> data;
}
