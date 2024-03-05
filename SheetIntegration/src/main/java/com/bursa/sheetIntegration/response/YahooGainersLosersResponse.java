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
public class YahooGainersLosersResponse {

	@JsonProperty("finance")
	private YahooGainersLosersFinance yahooGainersLosersFinance;

	@Getter
	@Setter
	public static class YahooGainersLosersFinance {

		@JsonProperty("result")
		private List<Result> result;

		@JsonProperty("error")
		private String error;

		@Getter
		@Setter
		public static class Result {
			@JsonProperty("id")
			private String id;

			@JsonProperty("title")
			private String title;

			@JsonProperty("description")
			private String description;

			@JsonProperty("canonicalName")
			private String canonicalName;

			@JsonProperty("criteriaMeta")
			private CriteriaMeta criteriaMeta;

			@JsonProperty("rawCriteria")
			private String rawCriteria;

			@JsonProperty("start")
			private long start;

			@JsonProperty("count")
			private long count;

			@JsonProperty("total")
			private long total;

			@JsonProperty("quotes")
			private List<GLQuote> quotes;

			@JsonProperty("useRecords")
			private boolean useRecords;

			@JsonProperty("predefinedScr")
			private boolean predefinedScr;

			@JsonProperty("versionId")
			private long versionId;

			@JsonProperty("creationDate")
			private long creationDate;

			@JsonProperty("lastUpdated")
			private long lastUpdated;

			@JsonProperty("isPremium")
			private boolean isPremium;

			@JsonProperty("iconUrl")
			private String iconUrl;
		}

		@Getter
		@Setter
		public static class CriteriaMeta {

			@JsonProperty("size")
			private long size;

			@JsonProperty("offset")
			private long offSet;

			@JsonProperty("sortField")
			private String sortField;

			@JsonProperty("sortType")
			private String sortType;

			@JsonProperty("quoteType")
			private String quoteType;

			@JsonProperty("criteria")
			private List<Criteria> criteria;

			@JsonProperty("topOperator")
			private String topOperator;

		}

		@Getter
		@Setter
		public static class Criteria {

			@JsonProperty("field")
			private String field;

			@JsonProperty("operators")
			private List<String> operators;

			@JsonProperty("values")
			private List<Double> values;

			@JsonProperty("labelsSelected")
			private List<Integer> labelsSelected;

			@JsonProperty("dependentValues")
			private List<Object> dependentValues;

		}

		@Getter
		@Setter
		public static class GLQuote {

			@JsonProperty("symbol")
			private String symbol;

			@JsonProperty("twoHundredDayAverageChangePercent")
			private BookValue twoHundredDayAverageChangePercent;

			@JsonProperty("dividendDate")
			private AverageDailyVolume10Day dividendDate;

			@JsonProperty("averageAnalystRating")
			private String averageAnalystRating;

			@JsonProperty("fiftyTwoWeekLowChangePercent")
			private BookValue fiftyTwoWeekLowChangePercent;

			@JsonProperty("language")
			private String language;

			@JsonProperty("preMarketChangePercent")
			private BookValue preMarketChangePercent;

			@JsonProperty("regularMarketDayRange")
			private RegularMarketDayRange regularMarketDayRange;

			@JsonProperty("earningsTimestampEnd")
			private AverageDailyVolume10Day earningsTimestampEnd;

			@JsonProperty("epsForward")
			private BookValue epsForward;

			@JsonProperty("regularMarketDayHigh")
			private BookValue regularMarketDayHigh;

			@JsonProperty("twoHundredDayAverageChange")
			private BookValue twoHundredDayAverageChange;

			@JsonProperty("twoHundredDayAverage")
			private BookValue twoHundredDayAverage;

			@JsonProperty("askSize")
			private AverageDailyVolume10Day askSize;

			@JsonProperty("bookValue")
			private BookValue bookValue;

			@JsonProperty("marketCap")
			private AverageDailyVolume10Day marketCap;

			@JsonProperty("fiftyTwoWeekHighChange")
			private BookValue fiftyTwoWeekHighChange;

			@JsonProperty("fiftyTwoWeekRange")
			private FiftyTwoWeekRange fiftyTwoWeekRange;

			@JsonProperty("fiftyDayAverageChange")
			private BookValue fiftyDayAverageChange;

			@JsonProperty("averageDailyVolume3Month")
			private AverageDailyVolume10Day averageDailyVolume3Month;

			@JsonProperty("firstTradeDateMilliseconds")
			private long firstTradeDateMilliseconds;

			@JsonProperty("exchangeDataDelayedBy")
			private long exchangeDataDelayedBy;

			@JsonProperty("trailingAnnualDividendRate")
			private BookValue trailingAnnualDividendRate;

			@JsonProperty("fiftyTwoWeekChangePercent")
			private BookValue fiftyTwoWeekChangePercent;

			@JsonProperty("fiftyTwoWeekLow")
			private BookValue fiftyTwoWeekLow;

			@JsonProperty("regularMarketVolume")
			private AverageDailyVolume10Day regularMarketVolume;

			@JsonProperty("market")
			private String market;

			@JsonProperty("quoteSourceName")
			private String quoteSourceName;

			@JsonProperty("messageBoardId")
			private String messageBoardId;

			@JsonProperty("priceHint")
			private long priceHint;

			@JsonProperty("exchange")
			private String exchange;

			@JsonProperty("sourceInterval")
			private long sourceInterval;

			@JsonProperty("regularMarketDayLow")
			private BookValue regularMarketDayLow;

			@JsonProperty("region")
			private String region;

			@JsonProperty("shortName")
			private String shortName;

			@JsonProperty("fiftyDayAverageChangePercent")
			private BookValue fiftyDayAverageChangePercent;

			@JsonProperty("preMarketTime")
			private BookValue preMarketTime;

			@JsonProperty("fullExchangeName")
			private String fullExchangeName;

			@JsonProperty("earningsTimestampStart")
			private AverageDailyVolume10Day earningsTimestampStart;

			@JsonProperty("financialCurrency")
			private String financialCurrency;

			@JsonProperty("displayName")
			private String displayName;

			@JsonProperty("gmtOffSetMilliseconds")
			private long gmtOffSetMilliseconds;

			@JsonProperty("regularMarketOpen")
			private BookValue regularMarketOpen;

			@JsonProperty("regularMarketTime")
			private BookValue regularMarketTime;

			@JsonProperty("regularMarketChangePercent")
			private BookValue regularMarketChangePercent;

			@JsonProperty("trailingAnnualDividendYield")
			private BookValue trailingAnnualDividendYield;

			@JsonProperty("quoteType")
			private String quoteType;

			@JsonProperty("fiftyTwoWeekLowChange")
			private BookValue fiftyTwoWeekLowChange;

			@JsonProperty("averageDailyVolume10Day")
			private AverageDailyVolume10Day averageDailyVolume10Day;

			@JsonProperty("fiftyTwoWeekHighChangePercent")
			private BookValue fiftyTwoWeekHighChangePercent;

			@JsonProperty("typeDisp")
			private String typeDisp;

			@JsonProperty("trailingPE")
			private BookValue trailingPE;

			@JsonProperty("lastClosePriceToNNWCPerShare")
			private BookValue lastClosePriceToNNWCPerShare;

			@JsonProperty("tradeable")
			private boolean tradeable;

			@JsonProperty("currency")
			private String currency;

			@JsonProperty("preMarketPrice")
			private BookValue preMarketPrice;

			@JsonProperty("sharesOutstanding")
			private AverageDailyVolume10Day sharesOutstanding;

			@JsonProperty("fiftyTwoWeekHigh")
			private BookValue fiftyTwoWeekHigh;

			@JsonProperty("regularMarketPreviousClose")
			private BookValue regularMarketPreviousClose;

			@JsonProperty("exchangeTimezoneName")
			private String exchangeTimezoneName;

			@JsonProperty("bidSize")
			private AverageDailyVolume10Day bidSize;

			@JsonProperty("regularMarketChange")
			private BookValue regularMarketChange;

			@JsonProperty("priceEpsCurrentYear")
			private BookValue priceEpsCurrentYear;

			@JsonProperty("cryptoTradeable")
			private boolean cryptoTradeable;

			@JsonProperty("fiftyDayAverage")
			private BookValue fiftyDayAverage;

			@JsonProperty("epsCurrentYear")
			private BookValue epsCurrentYear;

			@JsonProperty("exchangeTimezoneShortName")
			private String exchangeTimezoneShortName;

			@JsonProperty("regularMarketPrice")
			private BookValue regularMarketPrice;

			@JsonProperty("marketState")
			private String marketState;

			@JsonProperty("customPriceAlertConfidence")
			private String customPriceAlertConfidence;

			@JsonProperty("preMarketChange")
			private BookValue preMarketChange;

			@JsonProperty("forwardPE")
			private BookValue forwardPE;

			@JsonProperty("lastCloseTevEbitLtm")
			private BookValue lastCloseTevEbitLtm;

			@JsonProperty("earningsTimestamp")
			private AverageDailyVolume10Day earningsTimestamp;

			@JsonProperty("ask")
			private BookValue ask;

			@JsonProperty("epsTrailingTwelveMonths")
			private BookValue epsTrailingTwelveMonths;

			@JsonProperty("bid")
			private BookValue bid;

			@JsonProperty("priceToBook")
			private BookValue priceToBook;

			@JsonProperty("triggerable")
			private boolean triggerable;

			@JsonProperty("longName")
			private String longName;

			@JsonProperty("dividendYield")
			private BookValue dividendYield;

			@JsonProperty("postMarketPrice")
			private BookValue postMarketPrice;

			@JsonProperty("postMarketTime")
			private BookValue postMarketTime;

			@JsonProperty("postMarketChangePercent")
			private BookValue postMarketChangePercent;

			@JsonProperty("postMarketChange")
			private BookValue postMarketChange;

			@JsonProperty("ipoExpectedDate")
			private String ipoExpectedDate;

			@JsonProperty("dividendRate")
			private BookValue dividendRate;
			
			@JsonProperty("prevName")
			private String prevName;
			
			@JsonProperty("nameChangeDate")
			private String nameChangeDate;
			
			@JsonProperty("hasPrePostMarketData")
			private String hasPrePostMarketData;

		}

		@Getter
		@Setter
		public static class BookValue {

			@JsonProperty("raw")
			public double raw;

			@JsonProperty("fmt")
			public String fmt;
		}

		@Getter
		@Setter
		public static class AverageDailyVolume10Day {

			@JsonProperty("raw")
			public double raw;

			@JsonProperty("fmt")
			public String fmt;

			@JsonProperty("longFmt")
			public String longFmt;
		}

		public static class RegularMarketDayRange {

			@JsonProperty("raw")
			public String raw;

			@JsonProperty("fmt")
			public String fmt;

		}

		@Getter
		@Setter
		public static class FiftyTwoWeekRange {

			@JsonProperty("raw")
			public String raw;

			@JsonProperty("fmt")
			public String fmt;
		}

		@Getter
		@Setter
		public static class CriteriaQuery {
			@JsonProperty("operator")
			private String operator;

			@JsonProperty("operands")
			private CriteriaOperand[] operands;
		}

		@Getter
		@Setter
		public static class CriteriaOperand {
			@JsonProperty("operator")
			private String operator;

			@JsonProperty("operands")
			private Object[] operands;
		}
	}

}
