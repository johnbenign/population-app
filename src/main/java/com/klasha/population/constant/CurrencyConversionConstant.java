package com.klasha.population.constant;

public enum CurrencyConversionConstant {
    EUR_NGN (493.06), NGN_EUR(1/493.06),
    USD_NGN(460.72), NGN_USD(1/460.72),
    JPY_NGN(3.28), NGN_JPY(1/3.28),
    GBP_NGN(570.81), NGN_GBP(1/570.81),
    EUR_UGX(4.33), UGX_EUR(1/4.33),
    USD_UGX(739.83), UGX_USD(1/739.83),
    JPY_UGX(26.62), UGX_JPY(1/26.62),
    GBP_UGX(633.48), UGX_GBP(1/633.48);

    private double rate;

    CurrencyConversionConstant(double rate){
        this.rate = rate;
    }

    public double getRate() {
        return rate;
    }
}
