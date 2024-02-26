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
public class NewsResponse {
	
	@JsonProperty("items")
	private String items;
	
	@JsonProperty("sentiment_score_definition")
	private String sentimentScoreDefinition;
	
	@JsonProperty("relevance_score_definition")
	private String relevanceScoreDefinition;
	
	@JsonProperty("feed")
	private List<Feed> feed;
	
	@Getter
	@Setter
	public static class Feed {
		
		@JsonProperty("title")
		private String title;
		
		@JsonProperty("url")
		private String url;
		
		@JsonProperty("time_published")
		private String time_published;
		
		@JsonProperty("authors")
		private List<String> authors;
		
		@JsonProperty("summary")
		private String summary;
		
		@JsonProperty("banner_image")
		private String bannerImage;
		
		@JsonProperty("source")
		private String source;
		
		@JsonProperty("category_within_source")
		private String categoryWithinSource;
		
		@JsonProperty("source_domain")
		private String sourceDomain;
		
		@JsonProperty("topics")
		private List<Topics> topics;
		
		@JsonProperty("overall_sentiment_score")
		private String overallSentimentScore;
		
		
		@JsonProperty("overall_sentiment_label")
		private String overallSentimentLabel;
		
		@JsonProperty("ticker_sentiment")
		private List<TickerSentiment> tickerSentiment;
		
	}
	
	@Getter
	@Setter
	public static class Topics {
		
		@JsonProperty("topic")
		private String topic;
		
		@JsonProperty("relevance_score")
		private String relevanceScore;
		
	}
	
	@Getter
	@Setter
	public static class TickerSentiment {
		
		@JsonProperty("ticker")
		private String ticker;
		
		@JsonProperty("relevance_score")
		private String relevanceScore;
		
		@JsonProperty("ticker_sentiment_score")
		private String tickerSentimentScore;
		
		@JsonProperty("ticker_sentiment_label")
		private String tickerSentimentLabel;
		
	}

}
