package com.bursa.sheetIntegration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bursa.sheetIntegration.response.Response;
import com.bursa.sheetIntegration.service.YahooService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class YahooController {
	
	@Autowired
	private YahooService yahooService;
	
	@GetMapping("/trendingTickers")
	public ResponseEntity<Response> getTrendingTickers(@RequestParam("region") String region) {
		Response response = yahooService.getTrendingTickers(region);
		return ResponseEntity.ok(response);
	}

}
