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

import com.bursa.sheetIntegration.service.GoogleSheetsService;

@RestController
@RequestMapping("/api")
public class SheetsController {

	@Autowired
	private GoogleSheetsService sheetsService;

	@GetMapping("/row")
	public ResponseEntity<List<Map<String, Object>>> getSheetData(@RequestParam("search") String symbol,
			@RequestParam("type") String type) {
		try {

			List<Map<String, Object>> sheetData = sheetsService.getSheetData(symbol, type);
			return ResponseEntity.ok(sheetData);
		} catch (IOException | GeneralSecurityException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/column")
	public ResponseEntity<List<Map<String, Object>>> getSheetDataColumn(@RequestParam("search") String symbol,
			@RequestParam("type") String type, @RequestParam(name = "report", required = false) boolean report,
			@RequestParam(name = "year", required = false) String year) {

		try {
			List<Map<String, Object>> sheetData = sheetsService.getSheetDataColumn(symbol, type, report, year);
			return ResponseEntity.ok(sheetData);
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}
	
}