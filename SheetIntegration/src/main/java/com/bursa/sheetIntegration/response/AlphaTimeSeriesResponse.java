package com.bursa.sheetIntegration.response;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlphaTimeSeriesResponse {
	
	@JsonProperty("Meta Data")
	private MetaData metaData;
	
	@JsonProperty("Time Series (Daily)")
	private Map<String, TimeSeriesDaily> timeSeriesDaily;
	
	@Getter
	@Setter
	public static class MetaData {
		
		@JsonProperty("1. Information")
		private String information;
		
		@JsonProperty("2. Symbol")
		private String symbol;
		
		@JsonProperty("3. Last Refreshed")
		private String lastRefreshed;
		
		@JsonProperty("4. Output Size")
		private String outputSize;
		
		@JsonProperty("5. Time Zone")
		private String timeZone;
		
	}
	
	@Getter
	@Setter
	public static class TimeSeriesDaily {
		
		@JsonProperty("1. open")
		private String open;
		
		@JsonProperty("2. high")
		private String high;
		
		@JsonProperty("3. low")
		private String low;
		
		@JsonProperty("4. close")
		private String close;
		
		@JsonProperty("5. volume")
		private String volume;
		
	}

}
