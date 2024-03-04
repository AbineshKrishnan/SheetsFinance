package com.bursa.sheetIntegration.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlphaQuoteResponse {

	@JsonProperty(value = "Global Quote")
	private GlobalQuote globalQuote;

	@Getter
	@Setter
	public static class GlobalQuote {
		@JsonProperty(value = "01. symbol")
		private String symbol;

		@JsonProperty(value = "02. open")
		private String open;

		@JsonProperty(value = "03. high")
		private String high;

		@JsonProperty(value = "04. low")
		private String low;

		@JsonProperty(value = "05. price")
		private String price;

		@JsonProperty(value = "06. volume")
		private String volume;

		@JsonProperty(value = "07. latest trading day")
		private String latestTradingDay;

		@JsonProperty(value = "08. previous close")
		private String previousClose;

		@JsonProperty(value = "09. change")
		private String change;

		@JsonProperty(value = "10. change percent")
		private String changePercent;
	}

}
