package com.bursa.sheetIntegration.service;

import com.bursa.sheetIntegration.response.Response;

public interface YahooService {

	Response getTrendingTickers(String region);

	Response gainersLosersAndMostActives(String request, int count);

}
