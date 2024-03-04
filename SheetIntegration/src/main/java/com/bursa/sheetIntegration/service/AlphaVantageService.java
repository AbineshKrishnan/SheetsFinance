package com.bursa.sheetIntegration.service;

import com.bursa.sheetIntegration.response.Response;

public interface AlphaVantageService {

	Response getTickerNews(String symbol);

	Response getTimeSeries(String symbol);

	Response getCompanyInfo(String symbol);

	Response getBalanceSheet(String symbol);

	Response getQuoteEndpoint(String symbol);

	Response getIncomeStatement(String symbol);

	Response getCashFlow(String symbol);

	Response getTopGainersAndLosers();

}
