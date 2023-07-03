package com.klasha.population.dto.response;

import com.klasha.population.dto.response.CapitalDetail;
import lombok.Data;

@Data
public class CapitalDetailResponse {
    private boolean error;
    private int status;
    private String msg;
    private CapitalDetail data;
}
