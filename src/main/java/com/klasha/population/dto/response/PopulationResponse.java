package com.klasha.population.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PopulationResponse{
    private boolean error;
    private int status;
    private String msg;
    private List<Population> data;
}
