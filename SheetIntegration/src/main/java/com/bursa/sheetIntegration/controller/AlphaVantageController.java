package com.bursa.sheetIntegration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bursa.sheetIntegration.response.Response;
import com.bursa.sheetIntegration.service.AlphaVantageService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class AlphaVantageController {
	
	@Autowired
	AlphaVantageService service;
	
	@GetMapping("/getTickerNews")
	public ResponseEntity<Response> getnews(@RequestParam("symbol") String symbol) {
		Response response = service.getTickerNews(symbol);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/getTimeSeries")
	public ResponseEntity<Response> getTimeSeries(@RequestParam("symbol") String symbol) {
		Response response = service.getCompanyInfo(symbol);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/getTimeSeries")
	public ResponseEntity<Response> getCompanyInfo(@RequestParam("symbol") String symbol) {
		Response response = service.getTimeSeries(symbol);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/getTimeSeries")
	public ResponseEntity<Response> getBalanceSheet(@RequestParam("symbol") String symbol) {
		Response response = service.getBalanceSheet(symbol);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/getTimeSeries")
	public ResponseEntity<Response> getQuoteEndpoint(@RequestParam("symbol") String symbol) {
		Response response = service.getQuoteEndpoint(symbol);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/getTimeSeries")
	public ResponseEntity<Response> getIncomeStatement(@RequestParam("symbol") String symbol) {
		Response response = service.getIncomeStatement(symbol);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/getTimeSeries")
	public ResponseEntity<Response> getCashFlow(@RequestParam("symbol") String symbol) {
		Response response = service.getCashFlow(symbol);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/getTimeSeries")
	public ResponseEntity<Response> getTopGainersAndLosers() {
		Response response = service.getTopGainersAndLosers();
		return ResponseEntity.ok(response);
	}

}
