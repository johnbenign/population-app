package com.klasha.population.dto.request;

import com.klasha.population.constant.CurrencyConstant;
import lombok.Data;

@Data
public class CurrencyConversionRequest {
    private String country;
    private double amount;
    private CurrencyConstant targetCurrency;

    public static CurrencyConversionRequest createConversionRequest(String country, double amount, CurrencyConstant targetCurrency) {
        CurrencyConversionRequest request = new CurrencyConversionRequest();

        request.setAmount(amount);
        request.setCountry(country);
        request.setTargetCurrency(targetCurrency);

        return request;
    }
}
