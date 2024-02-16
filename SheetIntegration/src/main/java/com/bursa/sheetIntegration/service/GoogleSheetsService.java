package com.bursa.sheetIntegration.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;

public interface GoogleSheetsService {
	List<Map<String, Object>> getSheetData(String symbol, String type) throws IOException, GeneralSecurityException;

	List<Map<String, Object>> getSheetDataColumn(String symbol, String type, boolean report, String year)
			throws IOException;
}
