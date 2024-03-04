package com.bursa.sheetIntegration.serviceimpl;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bursa.sheetIntegration.response.AlphaBalanceSheetResponse;
import com.bursa.sheetIntegration.response.AlphaCashFlowResponse;
import com.bursa.sheetIntegration.response.AlphaCompanyInfoResponse;
import com.bursa.sheetIntegration.response.AlphaIncomeStatementResponse;
import com.bursa.sheetIntegration.response.AlphaQuoteResponse;
import com.bursa.sheetIntegration.response.AlphaTimeSeriesResponse;
import com.bursa.sheetIntegration.response.AlphaTopGainersAndLosersResponse;
import com.bursa.sheetIntegration.response.NewsResponse;
import com.bursa.sheetIntegration.response.Response;
import com.bursa.sheetIntegration.response.NewsResponse.Feed;
import com.bursa.sheetIntegration.service.AlphaVantageService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlphaVantageServiceImpl implements AlphaVantageService {

	@Value("${alphavantage.apikey}")
	private String apiKey;

	public Response getCompanyInfo(String symbol) {
		Response response = new Response();
		try {
			String newsUrl = "https://www.alphavantage.co/query?function=OVERVIEW&symbol=" + symbol + "&apikey="
					+ apiKey;
			HttpRequest newsRequest = HttpRequest.newBuilder().uri(URI.create(newsUrl)).build();
			HttpResponse<String> newsResponse = HttpClient.newHttpClient().send(newsRequest,
					HttpResponse.BodyHandlers.ofString());

			ObjectMapper objectNewsMapper = new ObjectMapper();
			AlphaCompanyInfoResponse annualReports = objectNewsMapper.readValue(newsResponse.body(),
					AlphaCompanyInfoResponse.class);

			response.setStatus(true);
			response.setMessage("Success");
			response.setStatusCode(HttpStatus.OK.value());
			response.setRetrievedResult(annualReports);
			return response;

		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(false);
			response.setMessage("INTERNAL SERVER ERROR");
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setRetrievedResult(null);
			return response;
		}
	}

	public Response getBalanceSheet(String symbol) {
		Response response = new Response();
		try {

			String url = "https://www.alphavantage.co/query?function=BALANCE_SHEET&symbol=" + symbol + "&apikey="
					+ apiKey;

			HttpRequest balanceRequest = HttpRequest.newBuilder().uri(URI.create(url)).build();
			HttpResponse<String> balanceResponse = HttpClient.newHttpClient().send(balanceRequest,
					HttpResponse.BodyHandlers.ofString());

			ObjectMapper objectMapper = new ObjectMapper();
			AlphaBalanceSheetResponse balanceResponseInfo = objectMapper.readValue(balanceResponse.body(),
					AlphaBalanceSheetResponse.class);

			response.setStatus(true);
			response.setMessage("Success");
			response.setStatusCode(HttpStatus.OK.value());
			response.setRetrievedResult(balanceResponseInfo);
			return response;

		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(false);
			response.setMessage("INTERNAL SERVER ERROR");
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setRetrievedResult(null);
			return response;
		}
	}

	public Response getQuoteEndpoint(String symbol) {
		Response response = new Response();
		try {

			String url = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=" + symbol + "&apikey="
					+ apiKey;
			HttpRequest quoteRequest = HttpRequest.newBuilder().uri(URI.create(url)).build();
			HttpResponse<String> quoteResponse = HttpClient.newHttpClient().send(quoteRequest,
					HttpResponse.BodyHandlers.ofString());

			ObjectMapper objectMapper = new ObjectMapper();
			AlphaQuoteResponse globalQuote = objectMapper.readValue(quoteResponse.body(), AlphaQuoteResponse.class);

			response.setStatus(true);
			response.setMessage("Success");
			response.setStatusCode(HttpStatus.OK.value());
			response.setRetrievedResult(globalQuote);
			return response;

		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(false);
			response.setMessage("INTERNAL SERVER ERROR");
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setRetrievedResult(null);
			return response;
		}
	}

	public Response getIncomeStatement(String symbol) {
		Response response = new Response();
		try {
			String url = "https://www.alphavantage.co/query?function=INCOME_STATEMENT&symbol=" + symbol + "&apikey="
					+ apiKey;

			HttpClient httpClient = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

			HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			String responseBody = httpResponse.body();

			ObjectMapper objectMapper = new ObjectMapper();
			AlphaIncomeStatementResponse incomeStatementResponse = objectMapper.readValue(responseBody,
					AlphaIncomeStatementResponse.class);

			response.setStatus(true);
			response.setMessage("Success");
			response.setStatusCode(HttpStatus.OK.value());
			response.setRetrievedResult(incomeStatementResponse);
			return response;
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			response.setStatus(false);
			response.setMessage("INTERNAL SERVER ERROR");
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setRetrievedResult(null);
			return response;
		}
	}

	public Response getCashFlow(String search) {
		Response response = new Response();
		try {
			String url = "https://www.alphavantage.co/query?function=CASH_FLOW&symbol=" + search + "&apikey=" + apiKey;

			HttpClient httpClient = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

			HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			String responseBody = httpResponse.body();

			ObjectMapper objectMapper = new ObjectMapper();
			AlphaCashFlowResponse cashFlowResponse = objectMapper.readValue(responseBody, AlphaCashFlowResponse.class);

			response.setStatus(true);
			response.setMessage("Success");
			response.setStatusCode(HttpStatus.OK.value());
			response.setRetrievedResult(cashFlowResponse);
			return response;
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			response.setStatus(false);
			response.setMessage("INTERNAL SERVER ERROR");
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setRetrievedResult(null);
			return response;
		}
	}

	public Response getTopGainersAndLosers() {
		Response response = new Response();
		try {
			String url = "https://www.alphavantage.co/query?function=TOP_GAINERS_LOSERS&apikey=" + apiKey;

			HttpClient httpClient = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

			HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			String responseBody = httpResponse.body();

			ObjectMapper objectMapper = new ObjectMapper();
			AlphaTopGainersAndLosersResponse topGainersAndLosersResponse = objectMapper.readValue(responseBody,
					AlphaTopGainersAndLosersResponse.class);

			response.setStatus(true);
			response.setMessage("Success");
			response.setStatusCode(HttpStatus.OK.value());
			response.setRetrievedResult(topGainersAndLosersResponse);
			return response;

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			response.setStatus(false);
			response.setMessage("INTERNAL SERVER ERROR");
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setRetrievedResult(null);
			return response;
		}
	}
	
	public Response getTickerNews(String symbol) {
		Response response = new Response();
		try {

			LocalDate currentDate = LocalDate.now().minusDays(5);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
			String formattedDate = currentDate.format(formatter);

			String newsUrl = "https://www.alphavantage.co/query?function=NEWS_SENTIMENT&tickers=" + symbol + "&apikey="
					+ apiKey + "&limit=10&time_from=" + formattedDate + "T1200";
			HttpRequest newsRequest = HttpRequest.newBuilder().uri(URI.create(newsUrl)).build();
			HttpResponse<String> newsResponse = HttpClient.newHttpClient().send(newsRequest,
					HttpResponse.BodyHandlers.ofString());

			ObjectMapper objectNewsMapper = new ObjectMapper();
			NewsResponse alphaNewsResponse = objectNewsMapper.readValue(newsResponse.body(), NewsResponse.class);

			List<Feed> feedList = new ArrayList<>();
			if (alphaNewsResponse.getFeed().size() > 5) {
				for (int i = 0; i <= 4; i++) {

					feedList.add(alphaNewsResponse.getFeed().get(i));
				}
				alphaNewsResponse.setFeed(feedList);
				response.setStatus(true);
				response.setMessage("Success");
				response.setStatusCode(HttpStatus.OK.value());
				response.setRetrievedResult(alphaNewsResponse);
				return response;
			}
			response.setStatus(true);
			response.setMessage("Success");
			response.setStatusCode(HttpStatus.OK.value());
			response.setRetrievedResult(alphaNewsResponse);
			return response;

		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(false);
			response.setMessage("INTERNAL SERVER ERROR");
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setRetrievedResult(null);
			return response;
		}
	}

	public Response getTimeSeries(String symbol) {
		Response response = new Response();
		try {
			String newsUrl = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" + symbol
					+ "&apikey=" + apiKey;
			HttpRequest newsRequest = HttpRequest.newBuilder().uri(URI.create(newsUrl)).build();
			HttpResponse<String> newsResponse = HttpClient.newHttpClient().send(newsRequest,
					HttpResponse.BodyHandlers.ofString());

			ObjectMapper objectNewsMapper = new ObjectMapper();
			AlphaTimeSeriesResponse alphaTimeSerirsResponse = objectNewsMapper.readValue(newsResponse.body(),
					AlphaTimeSeriesResponse.class);

			response.setStatus(true);
			response.setMessage("Success");
			response.setStatusCode(HttpStatus.OK.value());
			response.setRetrievedResult(alphaTimeSerirsResponse);
			return response;
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			response.setStatus(false);
			response.setMessage("INTERNAL SERVER ERROR");
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setRetrievedResult(null);
			return response;
		}
	}

}
