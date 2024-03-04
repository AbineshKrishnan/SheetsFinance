package com.bursa.sheetIntegration.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlphaIncomeStatementResponse {

   @JsonProperty("symbol")
   private String symbol;

   @JsonProperty("annualReports")
   private List<AnnualReports> annualReports;

   @Getter
   @Setter
   public static class AnnualReports {
       @JsonProperty("fiscalDateEnding")
       private String fiscalDateEnding;

       @JsonProperty("reportedCurrency")
       private String reportedCurrency;

       @JsonProperty("grossProfit")
       private String grossProfit;

       @JsonProperty("totalRevenue")
       private String totalRevenue;

       @JsonProperty("costOfRevenue")
       private String costOfRevenue;

       @JsonProperty("costofGoodsAndServicesSold")
       private String costofGoodsAndServicesSold;

       @JsonProperty("operatingIncome")
       private String operatingIncome;

       @JsonProperty("sellingGeneralAndAdministrative")
       private String sellingGeneralAndAdministrative;

       @JsonProperty("researchAndDevelopment")
       private String researchAndDevelopment;

       @JsonProperty("operatingExpenses")
       private String operatingExpenses;

       @JsonProperty("investmentIncomeNet")
       private String investmentIncomeNet;

       @JsonProperty("netInterestIncome")
       private String netInterestIncome;

       @JsonProperty("interestIncome")
       private String interestIncome;

       @JsonProperty("interestExpense")
       private String interestExpense;

       @JsonProperty("nonInterestIncome")
       private String nonInterestIncome;

       @JsonProperty("otherNonOperatingIncome")
       private String otherNonOperatingIncome;

       @JsonProperty("depreciation")
       private String depreciation;

       @JsonProperty("depreciationAndAmortization")
       private String depreciationAndAmortization;

       @JsonProperty("incomeBeforeTax")
       private String incomeBeforeTax;

       @JsonProperty("incomeTaxExpense")
       private String incomeTaxExpense;

       @JsonProperty("interestAndDebtExpense")
       private String interestAndDebtExpense;

       @JsonProperty("netIncomeFromContinuingOperations")
       private String netIncomeFromContinuingOperations;

       @JsonProperty("comprehensiveIncomeNetOfTax")
       private String comprehensiveIncomeNetOfTax;

       @JsonProperty("ebit")
       private String ebit;

       @JsonProperty("ebitda")
       private String ebitda;

       @JsonProperty("netIncome")
       private String netIncome;

   }
}