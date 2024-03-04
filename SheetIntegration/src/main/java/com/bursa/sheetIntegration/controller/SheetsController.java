package com.bursa.sheetIntegration.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bursa.sheetIntegration.response.Response;
import com.bursa.sheetIntegration.service.GoogleSheetsService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class SheetsController {

	@Autowired
	private GoogleSheetsService sheetsService;

	@GetMapping("/realTimeBatch")
	public ResponseEntity<Response> realTimeBatch(@RequestParam("symbol") String symbol) {
		try {
			String formula = "=SF(\"" + symbol + "\",\"realTime\",\"all\")";
			String sheetName = "Real Time Batch";
			Response sheetData = sheetsService.getSheetData(symbol, formula, sheetName);
			return ResponseEntity.ok(sheetData);
		} catch (IOException | GeneralSecurityException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/companyInfo")
	public ResponseEntity<Response> companyInfo(@RequestParam("symbol") String symbol) {
		try {
			String formula = "=SF(\"" + symbol + "\",\"companyInfo\",\"all\")";
			String sheetName = "Company Info";
			Response sheetData = sheetsService.getSheetData(symbol, formula, sheetName);
			return ResponseEntity.ok(sheetData);
		} catch (IOException | GeneralSecurityException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/timeSeries")
	public ResponseEntity<Response> timeSeries(@RequestParam("symbol") String symbol,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {
		try {
			String formula = "=SF_TIMESERIES(\"" + symbol + "\",\"" + fromDate + "\",\"" + toDate + "\",\"\",\"all\")";
			String sheetName = "Time Series";
			Response sheetData = sheetsService.getSheetDataTimeSeries(formula, sheetName);
			return ResponseEntity.ok(sheetData);
		} catch (IOException | GeneralSecurityException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/balanceSheet")
	public ResponseEntity<Response> balanceSheet(@RequestParam("symbol") String symbol,
			@RequestParam(name = "type") String type) {
		try {
			String formula;
			String sheetName = "Balance Sheet";
			if (type.equalsIgnoreCase("Quarterly")) {
				formula = "=SF(\"" + symbol + "\",\"historicalFinancialsBalanceQ\",\"all\",\"2019-2023\")";
			} else {
				formula = "=SF(\"" + symbol + "\",\"historicalFinancialsBalance\",\"all\",\"2019-2023\")";
			}
			Response sheetData = sheetsService.getSheetDataColumn(formula, sheetName);
			return ResponseEntity.ok(sheetData);
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	@GetMapping("/incomeStatement")
	public ResponseEntity<Response> incomeStatement(@RequestParam("symbol") String symbol,
			@RequestParam(name = "type") String type) {
		try {
			String formula;
			String sheetName = "Income Statement";
			if (type.equalsIgnoreCase("Quarterly")) {
				formula = "=SF(\"" + symbol + "\",\"historicalFinancialsIncomeQ\",\"all\",\"2019-2023\")";
			} else {
				formula = "=SF(\"" + symbol + "\",\"historicalFinancialsIncome\",\"all\",\"2019-2023\")";
			}
			Response sheetData = sheetsService.getSheetDataColumn(formula, sheetName);
			return ResponseEntity.ok(sheetData);
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/cashFlow")
	public ResponseEntity<Response> cashFlow(@RequestParam("symbol") String symbol,
			@RequestParam(name = "type") String type) {
		try {
			String formula;
			String sheetName = "Cash Flow";
			if (type.equalsIgnoreCase("Quarterly")) {
				formula = "=SF(\"" + symbol + "\",\"historicalFinancialsCashQ\",\"all\",\"2019-2023\")";
			} else {
				formula = "=SF(\"" + symbol + "\",\"historicalFinancialsCash\",\"all\",\"2019-2023\")";
			}
			Response sheetData = sheetsService.getSheetDataColumn(formula, sheetName);
			return ResponseEntity.ok(sheetData);
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/symbolSearch")
	public ResponseEntity<Response> symbolSearch(@RequestParam("search") String search) {
		Response response = sheetsService.symbolSearch(search);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/prepostMarket")
	public ResponseEntity<Response> prepostMarket(@RequestParam("symbol") String symbol) {
		try {
			String formula = "=SF(\"" + symbol + "\",\"prePostMarket\",\"all\")";
			String sheetName = "Prepost Market";
			Response sheetData = sheetsService.getSheetData(symbol, formula, sheetName);
			return ResponseEntity.ok(sheetData);
		} catch (IOException | GeneralSecurityException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}