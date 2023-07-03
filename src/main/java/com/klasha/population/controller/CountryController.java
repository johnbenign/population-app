package com.klasha.population.controller;

import com.klasha.population.constant.ApiRoute;
import com.klasha.population.constant.CurrencyConstant;
import com.klasha.population.dto.request.CurrencyConversionRequest;
import com.klasha.population.dto.response.GeneralResponse;
import com.klasha.population.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/countries")
@RequiredArgsConstructor
public class CountryController {
    private final CountryService service;

    @GetMapping(ApiRoute.GET_CITY_BY_POPULATION)
    public GeneralResponse getCitiesByPopulation(@RequestParam("number_of_cities") Long numberOfCities){
        return service.getCitiesByPopulation(numberOfCities);
    }

    @GetMapping(ApiRoute.GET_COUNTRY_DATA)
    public GeneralResponse getCountryData(@RequestParam String country){
        return service.getCountryData(country);
    }

    @GetMapping(ApiRoute.RETRIEVE_STATE_DETAIL)
    public GeneralResponse retrieveStateDetails(@RequestParam String country){
        return service.getStateDetails(country);
    }

    @PostMapping(ApiRoute.CONVERT_CURRENCY)
    public GeneralResponse convertCurrency(@RequestParam String country, @RequestParam double amount, @RequestParam("target-currency") CurrencyConstant targetCurrency){
        CurrencyConversionRequest request = CurrencyConversionRequest.createConversionRequest(country, amount, targetCurrency);
        return service.convertCurrency(request);
    }
}
