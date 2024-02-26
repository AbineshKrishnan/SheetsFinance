package com.bursa.sheetIntegration.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;

import com.bursa.sheetIntegration.response.NewsResponse;
import com.bursa.sheetIntegration.response.SymbolSearchResponse;

public interface GoogleSheetsService {
	List<Map<String, Object>> getSheetData(String symbol, String formula, String sheetName)
			throws IOException, GeneralSecurityException;

	List<Map<String, Object>> getSheetDataColumn(String formula, String sheetName) throws IOException;

	List<Map<String, Object>> getSheetDataTimeSeries(String formula, String sheetName)
			throws IOException, GeneralSecurityException;

	SymbolSearchResponse symbolSearch(String search);

	NewsResponse getTickerNews(String search);

}
