package com.bursa.sheetIntegration.service;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.bursa.sheetIntegration.response.Response;

public interface GoogleSheetsService {
	Response getSheetData(String symbol, String formula, String sheetName)
			throws IOException, GeneralSecurityException;

	Response getSheetDataColumn(String formula, String sheetName) throws IOException;

	Response getSheetDataTimeSeries(String formula, String sheetName)
			throws IOException, GeneralSecurityException;

	Response symbolSearch(String search);

	Response getTickerNews(String Symbol);

	Response getTimeSeries(String symbol);

}
