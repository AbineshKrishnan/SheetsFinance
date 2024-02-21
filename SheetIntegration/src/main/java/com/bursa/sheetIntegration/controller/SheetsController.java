package com.bursa.sheetIntegration.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bursa.sheetIntegration.response.SymbolSearchResponse;
import com.bursa.sheetIntegration.service.GoogleSheetsService;

@RestController
@RequestMapping("/api")
public class SheetsController {

	@Autowired
	private GoogleSheetsService sheetsService;

	@GetMapping("/realTimeBatch")
	public ResponseEntity<List<Map<String, Object>>> realTimeBatch(@RequestParam("symbol") String symbol) {
		try {
			String formula = "=SF(\"" + symbol + "\",\"realTime\",\"all\")";
			String sheetName="Real Time Batch";
			List<Map<String, Object>> sheetData = sheetsService.getSheetData(symbol, formula,sheetName);
			return ResponseEntity.ok(sheetData);
		} catch (IOException | GeneralSecurityException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GetMapping("/companyInfo")
	public ResponseEntity<List<Map<String, Object>>> companyInfo(@RequestParam("symbol") String symbol) {
		try {
			String formula = "=SF(\"" + symbol + "\",\"companyInfo\",\"all\")";
			String sheetName="Company Info";
			List<Map<String, Object>> sheetData = sheetsService.getSheetData(symbol, formula,sheetName);
			return ResponseEntity.ok(sheetData);
		} catch (IOException | GeneralSecurityException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GetMapping("/timeSeries")
	public ResponseEntity<List<Map<String, Object>>> timeSeries(@RequestParam("symbol") String symbol, @RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {
		try {
			 String formula = "=SF_TIMESERIES(\""+symbol+"\",\""+fromDate+"\",\""+toDate+"\",\"\",\"all\")";
			String sheetName="Time Series";
			List<Map<String, Object>> sheetData = sheetsService.getSheetDataTimeSeries(formula, sheetName);
			return ResponseEntity.ok(sheetData);
		} catch (IOException | GeneralSecurityException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/balanceSheet")
	public ResponseEntity<List<Map<String, Object>>> balanceSheet(@RequestParam("symbol") String symbol, @RequestParam(name = "type") String type) {
		try {
			String formula;
			String sheetName="Balance Sheet";
			if(type.equalsIgnoreCase("Quarterly")) {
				formula="=SF(\"" + symbol + "\",\"historicalFinancialsBalanceQ\",\"all\",\"2019-2023\")";	
			}else {
				formula="=SF(\"" + symbol + "\",\"historicalFinancialsBalance\",\"all\",\"2019-2023\")";	
			}
			List<Map<String, Object>> sheetData = sheetsService.getSheetDataColumn(formula, sheetName);
			return ResponseEntity.ok(sheetData);
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}
	
	@GetMapping("/incomeStatement")
	public ResponseEntity<List<Map<String, Object>>> incomeStatement(@RequestParam("symbol") String symbol, @RequestParam(name = "type") String type) {
		try {
			String formula;
			String sheetName="Income Statement";
			if(type.equalsIgnoreCase("Quarterly")) {
				formula="=SF(\"" + symbol + "\",\"historicalFinancialsIncomeQ\",\"all\",\"2019-2023\")";	
			}else {
				formula="=SF(\"" + symbol + "\",\"historicalFinancialsIncome\",\"all\",\"2019-2023\")";	
			}
			List<Map<String, Object>> sheetData = sheetsService.getSheetDataColumn(formula, sheetName);
			return ResponseEntity.ok(sheetData);
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GetMapping("/cashFlow")
	public ResponseEntity<List<Map<String, Object>>> cashFlow(@RequestParam("symbol") String symbol, @RequestParam(name = "type") String type) {
		try {
			String formula;
			String sheetName="Cash Flow";
			if(type.equalsIgnoreCase("Quarterly")) {
				formula="=SF(\"" + symbol + "\",\"historicalFinancialsCashQ\",\"all\",\"2019-2023\")";	
			}else {
				formula="=SF(\"" + symbol + "\",\"historicalFinancialsCash\",\"all\",\"2019-2023\")";	
			}
			List<Map<String, Object>> sheetData = sheetsService.getSheetDataColumn(formula, sheetName);
			return ResponseEntity.ok(sheetData);
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GetMapping("/symbolSearch")
	public SymbolSearchResponse symbolSearch(@RequestParam("search") String search) {
		return sheetsService.symbolSearch(search);
	}
	
}