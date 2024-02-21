package com.bursa.sheetIntegration.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SymbolSearchResponse {
	
	private List<BursaSymbolSearchResponse> bursaSearchSymbolResponseList;
	
	private List<UsSymbolSearchResponse> UsSymbolSearchResponseList;
	
	@Getter
	@Setter
	public static class BursaSymbolSearchResponse {
		
		private String symbol;
		
		private String name;
		
		private String s3key;

	}
	
	@Getter
	@Setter
	public static class UsSymbolSearchResponse {
		
		private String symbol;
		
		private String name;
		
		private String s3key;

	}

}
