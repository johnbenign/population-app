package com.klasha.population.dto.response;

import lombok.Data;

@Data
public class CurrencyDetailResponse {
    private boolean error;
    private int status;
    private String msg;
    private CurrencyDetail data;
}
