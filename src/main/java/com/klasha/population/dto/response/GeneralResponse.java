package com.klasha.population.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeneralResponse {
    private boolean error;
    private int status;
    private String msg;
    private Object data;

    public GeneralResponse(int status, String message) {
        this.status = status;
        this.msg = message;
        this.setError(true);
    }
}
