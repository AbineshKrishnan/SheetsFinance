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
public class AlphaTopGainersAndLosersResponse {

   @JsonProperty(value = "metadata")
   private String metaData;
   
   @JsonProperty(value = "last_updated")
   private String lastUpdated;
   
   @JsonProperty(value = "top_gainers")
   private List<TopGainers> topGainers;
   
   @JsonProperty(value = "top_losers")
   private List<TopLosers> topLosers;
   
   @JsonProperty(value = "most_actively_traded")
   private List<MostActivelyTraded> mostActivelyTraded;
   
   @Getter
   @Setter
   public static class TopGainers{
       
       @JsonProperty(value = "ticker")
       private String ticker;
       
       @JsonProperty(value = "price")
       private String price;
       
       @JsonProperty(value = "change_amount")
       private String changeAmount;
       
       @JsonProperty(value = "change_percentage")
       private String changePercentage;
       
       @JsonProperty(value = "volume")
       private String volume;
   }
   
   @Getter
   @Setter
   public static class TopLosers{
       
       @JsonProperty(value = "ticker")
       private String ticker;
       
       @JsonProperty(value = "price")
       private String price;
       
       @JsonProperty(value = "change_amount")
       private String changeAmount;
       
       @JsonProperty(value = "change_percentage")
       private String changePercentage;
       
       @JsonProperty(value = "volume")
       private String volume;
   }
   
   @Getter
   @Setter
   public static class MostActivelyTraded{
       
       @JsonProperty(value = "ticker")
       private String ticker;
       
       @JsonProperty(value = "price")
       private String price;
       
       @JsonProperty(value = "change_amount")
       private String changeAmount;
       
       @JsonProperty(value = "change_percentage")
       private String changePercentage;
       
       @JsonProperty(value = "volume")
       private String volume;
   }
}