package com.klasha.population.service.impl;

import com.google.gson.Gson;
import com.klasha.population.constant.CurrencyConversionConstant;
import com.klasha.population.dto.response.CapitalDetailResponse;
import com.klasha.population.dto.request.CurrencyConversionRequest;
import com.klasha.population.dto.response.*;
import com.klasha.population.exception.BadRequestException;
import com.klasha.population.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    @Value("${population.cities.url}")
    private String populationCitiesUrl;

    @Value("${population.url}")
    private String populationUrl;

    @Value("${capital.url}")
    private String capitalUrl;

    @Value("${state.city.url}")
    private String cityUrl;

    @Value("${state.url}")
    private String stateUrl;

    private final WebClient webClient;
    private final RestTemplate restTemplate;

    private final Gson gson;

    @Override
    public GeneralResponse getCitiesByPopulation(long numberOfCities){
            List<String> countries = List.of("Italy", "New Zealand", "Ghana");
            CityAndPopulationDto response = getAllCitiesAndPopulationData();
            if (response.isError())
                throw new BadRequestException(response.getMsg());

            List<Population> populations = response.getData().stream()
                    .filter(p -> countries.contains(p.getCountry()))
                    .limit(numberOfCities)
                    .sorted((a, b) -> b.getPopulationCounts().get(0).getValue().compareTo(a.getPopulationCounts().get(0).getValue()))
                    .collect(Collectors.toList());

            return GeneralResponse.builder()
                    .msg("Successful")
                    .data(populations)
                    .status(HttpStatus.OK.value())
                    .build();
    }

    private CityAndPopulationDto getAllCitiesAndPopulationData(){
        String jsonString = webClient.get()
                .uri(populationCitiesUrl)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return gson.fromJson(jsonString, CityAndPopulationDto.class);
    }

    @Override
    public GeneralResponse getCountryData(String country){
        CountryData countryData = new CountryData();

        Population population = retrievePopulationData(country);
        CapitalDetail capitalDetail = retrieveCapitalData(country);
        LocationDetail locationDetail = retrieveLocationDetail(country);
        String currency = getCurrency(country);

        countryData.setCurrency(currency);
        countryData.setPopulation(population);
        countryData.setCapitalDetail(capitalDetail);
        countryData.setLocationDetail(locationDetail);

        return GeneralResponse.builder()
                .msg("Successful")
                .data(countryData)
                .status(HttpStatus.OK.value())
                .build();
    }

    private String getCurrency(String country) {
        CountryDto dto = new CountryDto();
        dto.setCountry(country);
        Mono<CountryDto> requestMono = Mono.just(dto);

        CurrencyDetailResponse response = webClient.post()
                .uri(capitalUrl)
                .body(requestMono, CountryDto.class)
                .retrieve()
                .bodyToMono(CurrencyDetailResponse.class)
                .block();
        if(response.isError())
            throw new BadRequestException(response.getMsg());
        CurrencyDetail currencyDetail = response.getData();
        return currencyDetail.getCurrency();
    }

    private LocationDetail retrieveLocationDetail(String country) {
        CountryDto dto = new CountryDto();
        dto.setCountry(country);
        Mono<CountryDto> requestMono = Mono.just(dto);

        LocationDetailResponse response = webClient.post()
                .uri(capitalUrl)
                .body(requestMono, CountryDto.class)
                .retrieve()
                .bodyToMono(LocationDetailResponse.class)
                .block();

        if(response.isError())
            throw new BadRequestException(response.getMsg());

        return response.getData();
    }

    private CapitalDetail retrieveCapitalData(String country) {
        CountryDto dto = new CountryDto();
        dto.setCountry(country);
        Mono<CountryDto> requestMono = Mono.just(dto);

        CapitalDetailResponse response = webClient.post()
                .uri(capitalUrl)
                .body(requestMono, CountryDto.class)
                .retrieve()
                .bodyToMono(CapitalDetailResponse.class)
                .block();
        if(response.isError())
            throw new BadRequestException(response.getMsg());
        return response.getData();
    }

    private Population retrievePopulationData(String country) {
        CountryDto dto = new CountryDto();
        dto.setCountry(country);

        Mono<CountryDto> requestMono = Mono.just(dto);

        PopulationResponse response = webClient.post()
                .uri(populationUrl)
                .contentType(MediaType.valueOf("application/json"))
                .body(requestMono, CountryDto.class)
                .accept(MediaType.valueOf("*/*"))
                .retrieve()
                .bodyToMono(PopulationResponse.class)
                .block();
        if(response.isError())
            throw new BadRequestException(response.getMsg());
        return response.getData();
    }

    @Override
    public GeneralResponse getStateDetails(String country){
        StateDetail stateDetail = retrieveStateDetails(country);

        return GeneralResponse.builder()
                .msg("Successful")
                .status(HttpStatus.OK.value())
                .data(stateDetail)
                .build();
    }

    private StateDetail retrieveStateDetails(String country) {
        CountryDto dto = new CountryDto();
        dto.setCountry(country);

        GeneralResponse response = webClient.post()
                .uri(stateUrl)
                .body(dto, CountryDto.class)
                .retrieve()
                .bodyToMono(GeneralResponse.class)
                .block();
        if(response.isError())
            throw new BadRequestException(response.getMsg());
        StateDetail stateDetail = (StateDetail) response.getData();
        stateDetail.getStates().forEach(state -> {
            String stateName = state.getName().replace("State","").trim();
            System.out.println("State Name : " + stateName);
            List<String> cities = getCitiesByState(country, stateName);
            state.setCities(cities);
        });
        return stateDetail;
    }

    private List<String> getCitiesByState(String country, String stateName) {
        CountryDto dto = new CountryDto();
        dto.setCountry(country);
//        dto.setState(stateName);

        GeneralResponse response = webClient.post()
                .uri(cityUrl)
                .body(dto, CountryDto.class)
                .retrieve()
                .bodyToMono(GeneralResponse.class)
                .block();
        if(response.isError())
            throw new BadRequestException(response.getMsg());
        return (List<String>) response.getData();
    }

    @Override
    public GeneralResponse convertCurrency(CurrencyConversionRequest request){
        List<String> validConversions = List.of(
                "EUR_UGX", "USD_NGN", "EUR_NGN", "JPY_NGN", "GBP_NGN",
                "NGN_USD", "NGN_EUR", "GBP_UGX", "JPY_UGX", "USD_UGX",
                "UGX_GBP", "UGX_JPY", "UGX_USD", "UGX_EUR", "NGN_GBP", "NGN_JPY");

        String sourceCurrency = getCurrency(request.getCountry());
        String conversion = sourceCurrency + "_" + request.getTargetCurrency();
        System.out.println("Converting::" + conversion);
        if(!validConversions.contains(conversion))
            throw new BadRequestException("Invalid Conversion, Please try again!");
        CurrencyConversionConstant conversionConstant = CurrencyConversionConstant.valueOf(conversion);
        double rate = conversionConstant.getRate();
        double convertedValue = request.getAmount() * rate;
        CurrencyConversionResponse response = new CurrencyConversionResponse();
        response.setCountryCurrency(sourceCurrency);
        response.setTargetAmount(request.getTargetCurrency() + " " + convertedValue);
        return GeneralResponse.builder()
                .msg("Successful")
                .data(response)
                .status(HttpStatus.OK.value())
                .build();
    }
}
