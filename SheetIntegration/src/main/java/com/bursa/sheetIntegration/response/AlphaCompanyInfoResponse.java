package com.bursa.sheetIntegration.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlphaCompanyInfoResponse {
	
    @JsonProperty(value = "Symbol")
    private String symbol;

    @JsonProperty(value = "AssetType")
    private String assetType;

    @JsonProperty(value = "Name")
    private String name;

    @JsonProperty(value = "Description")
    private String description;

    @JsonProperty(value = "CIK")
    private String cik;

    @JsonProperty(value = "Exchange")
    private String exchange;

    @JsonProperty(value = "Currency")
    private String currency;

    @JsonProperty(value = "Country")
    private String country;

    @JsonProperty(value = "Sector")
    private String sector;

    @JsonProperty(value = "Industry")
    private String industry;

    @JsonProperty(value = "Address")
    private String address;

    @JsonProperty(value = "FiscalYearEnd")
    private String fiscalYearEnd;

    @JsonProperty(value = "LatestQuarter")
    private String latestQuarter;

    @JsonProperty(value = "MarketCapitalization")
    private String marketCapitalization;

    @JsonProperty(value = "EBITDA")
    private String ebitda;

    @JsonProperty(value = "PERatio")
    private String peratio;

    @JsonProperty(value = "PEGRatio")
    private String pegratio;

    @JsonProperty(value = "BookValue")
    private String bookValue;

    @JsonProperty(value = "DividendPerShare")
    private String dividendPerShare;

    @JsonProperty(value = "DividendYield")
    private String dividendYield;

    @JsonProperty(value = "EPS")
    private String eps;

    @JsonProperty(value = "RevenuePerShareTTM")
    private String revenuePerShareTTM;

    @JsonProperty(value = "ProfitMargin")
    private String profitMargin;

    @JsonProperty(value = "OperatingMarginTTM")
    private String operatingMarginTTM;

    @JsonProperty(value = "ReturnOnAssetsTTM")
    private String returnOnAssetsTTM;

    @JsonProperty(value = "ReturnOnEquityTTM")
    private String returnOnEquityTTM;

    @JsonProperty(value = "RevenueTTM")
    private String revenueTTM;

    @JsonProperty(value = "GrossProfitTTM")
    private String grossProfitTTM;

    @JsonProperty(value = "DilutedEPSTTM")
    private String dilutedEPSTTM;

    @JsonProperty(value = "QuarterlyEarningsGrowthYOY")
    private String quarterlyEarningsGrowthYOY;

    @JsonProperty(value = "QuarterlyRevenueGrowthYOY")
    private String quarterlyRevenueGrowthYOY;

    @JsonProperty(value = "AnalystTargetPrice")
    private String analystTargetPrice;

    @JsonProperty(value = "AnalystRatingStrongBuy")
    private String analystRatingStrongBuy;

    @JsonProperty(value = "AnalystRatingBuy")
    private String analystRatingBuy;

    @JsonProperty(value = "AnalystRatingHold")
    private String analystRatingHold;

    @JsonProperty(value = "AnalystRatingSell")
    private String analystRatingSell;

    @JsonProperty(value = "AnalystRatingStrongSell")
    private String analystRatingStrongSell;

    @JsonProperty(value = "TrailingPE")
    private String trailingPE;

    @JsonProperty(value = "ForwardPE")
    private String forwardPE;

    @JsonProperty(value = "PriceToSalesRatioTTM")
    private String priceToSalesRatioTTM;

    @JsonProperty(value = "PriceToBookRatio")
    private String priceToBookRatio;

    @JsonProperty(value = "EVToRevenue")
    private String evtoRevenue;

    @JsonProperty(value = "EVToEBITDA")
    private String evtoEBITDA;

    @JsonProperty(value = "Beta")
    private String beta;

    @JsonProperty(value = "52WeekHigh")
    private String fiftyTwoWeekHigh;

    @JsonProperty(value = "52WeekLow")
    private String fiftyTwoWeekLow;

    @JsonProperty(value = "50DayMovingAverage")
    private String fiftyDayMovingAverage;

    @JsonProperty(value = "200DayMovingAverage")
    private String twoHundredDayMovingAverage;

    @JsonProperty(value = "SharesOutstanding")
    private String sharesOutstanding;

    @JsonProperty(value = "DividendDate")
    private String dividendDate;

    @JsonProperty(value = "ExDividendDate")
    private String exDividendDate;

}
