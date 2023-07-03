package com.klasha.population.dto.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class LocationDetail {

    @JsonAlias("long")
    private BigDecimal longitude;

    @JsonAlias("lat")
    private BigDecimal latitude;
}
