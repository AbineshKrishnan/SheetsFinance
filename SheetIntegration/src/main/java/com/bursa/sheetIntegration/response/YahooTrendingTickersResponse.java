package com.bursa.sheetIntegration.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class YahooTrendingTickersResponse {

	@JsonProperty("message")
	private String message;

	@JsonProperty("finance")
	private TrendingTickersFinance finance;

	@Getter
	@Setter
	@Accessors(chain = true)
	public static class TrendingTickersFinance {

		@JsonProperty("result")
		private List<TrendingTickersResult> result;

		@JsonProperty("error")
		private Object error;

		@Getter
		@Setter

		public static class TrendingTickersResult {

			@JsonProperty("count")
			private long count;

			@JsonProperty("quotes")
			private List<TrendingTickersQuote> quotes;

			@JsonProperty("jobTimestamp")
			private long jobTimestamp;

			@JsonProperty("startInterval")
			private long startInterval;
		}

		@Getter
		@Setter

		public static class TrendingTickersQuote {

			@JsonProperty("language")
			private String language;

			@JsonProperty("region")
			private String region;

			@JsonProperty("quoteType")
			private String quoteType;

			@JsonProperty("typeDisp")
			private String typeDisp;

			@JsonProperty("quoteSourceName")
			private String quoteSourceName;

			@JsonProperty("triggerable")
			private boolean triggerable;

			@JsonProperty("customPriceAlertConfidence")
			private String customPriceAlertConfidence;

			@JsonProperty("trendingScore")
			private Double trendingScore;

			@JsonProperty("sourceInterval")
			private long sourceInterval;

			@JsonProperty("exchangeDataDelayedBy")
			private long exchangeDataDelayedBy;

			@JsonProperty("exchangeTimezoneName")
			private String exchangeTimezoneName;

			@JsonProperty("exchangeTimezoneShortName")
			private String exchangeTimezoneShortName;

			@JsonProperty("gmtOffSetMilliseconds")
			private long gmtOffSetMilliseconds;

			@JsonProperty("esgPopulated")
			private boolean esgPopulated;

			@JsonProperty("tradeable")
			private boolean tradeable;

			@JsonProperty("cryptoTradeable")
			private boolean cryptoTradeable;

			@JsonProperty("regularMarketChange")
			private Double regularMarketChange;

			@JsonProperty("regularMarketChangePercent")
			private Double regularMarketChangePercent;

			@JsonProperty("regularMarketTime")
			private long regularMarketTime;

			@JsonProperty("regularMarketPrice")
			private Double regularMarketPrice;

			@JsonProperty("regularMarketPreviousClose")
			private Double regularMarketPreviousClose;

			@JsonProperty("exchange")
			private String exchange;

			@JsonProperty("market")
			private String market;

			@JsonProperty("fullExchangeName")
			private String fullExchangeName;

			@JsonProperty("shortName")
			private String shortName;

			@JsonProperty("longName")
			private String longName;

			@JsonProperty("marketState")
			private String marketState;

			@JsonProperty("firstTradeDateMilliseconds")
			private long firstTradeDateMilliseconds;

			@JsonProperty("priceHint")
			private long priceHint;

			@JsonProperty("symbol")
			private String symbol;

			@JsonProperty("imageUrl")
			private String imageUrl;

			@JsonProperty("hasPrePostMarketData")
			private boolean hasPrePostMarketData;

		}

	}
}