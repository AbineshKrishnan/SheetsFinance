package com.bursa.sheetIntegration.serviceimpl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bursa.sheetIntegration.entity.BursaSymbols;
import com.bursa.sheetIntegration.entity.UsSymbols;
import com.bursa.sheetIntegration.repository.BursaSymbolsRepository;
import com.bursa.sheetIntegration.repository.UsSymbolsRepository;
import com.bursa.sheetIntegration.response.BooleanResponse;
import com.bursa.sheetIntegration.response.Response;
import com.bursa.sheetIntegration.response.SymbolSearchResponse;
import com.bursa.sheetIntegration.response.SymbolSearchResponse.BursaSymbolSearchResponse;
import com.bursa.sheetIntegration.response.SymbolSearchResponse.UsSymbolSearchResponse;
import com.bursa.sheetIntegration.service.GoogleSheetsService;
import com.bursa.sheetIntegration.sheetconfig.SheetsQuickStart;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.CellData;
import com.google.api.services.sheets.v4.model.ExtendedValue;
import com.google.api.services.sheets.v4.model.GridCoordinate;
import com.google.api.services.sheets.v4.model.Request;
import com.google.api.services.sheets.v4.model.RowData;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.UpdateCellsRequest;
import com.google.api.services.sheets.v4.model.ValueRange;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

@Service
@RequiredArgsConstructor
public class GoogleSheetsServiceImpl implements GoogleSheetsService {

	private final BursaSymbolsRepository bursaSymbolsRepository;

	private final UsSymbolsRepository usSymbolsRepository;

	private String accessKey = "AKIAXYKJWNQTB4PFIC2A";

	private String secretkey = "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";

	private Region region = Region.AP_SOUTH_1;

	private String bucketName = "jahirs3";

	private String spreadsheetId = "18-wAjva5fGMT9xud1Af1f-zAlEGxaL-EeQezCYgXfn8";

	private static final String KEY_FILE_LOCATION = "/project/imagedrive-344109-3f52ed9e3cef.p12";
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static final String SERVICE_ACCOUNT_EMAIL = "sheetsfinance@imagedrive-344109.iam.gserviceaccount.com";
	private static final String APPLICATION_NAME = "Sheetsfinance";
	private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
	private static final org.apache.logging.log4j.Logger logger = LogManager
			.getLogger(SheetsQuickStart.class.getName());

	private Credential getCredentials(String KEY_FILE_LOCATION)
			throws URISyntaxException, IOException, GeneralSecurityException {
		URL fileURL = SheetsQuickStart.class.getClassLoader().getResource(KEY_FILE_LOCATION);
		if (fileURL == null) {
			fileURL = (new File(KEY_FILE_LOCATION)).toURI().toURL();
		}
		HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		return new GoogleCredential.Builder().setTransport(httpTransport).setJsonFactory(JSON_FACTORY)
				.setServiceAccountId(SERVICE_ACCOUNT_EMAIL)
				.setServiceAccountPrivateKeyFromP12File(new File(fileURL.toURI())).setServiceAccountScopes(SCOPES)
				.build();
	}

	public Response getSheetData(String symbol, String formula, String sheetName)
			throws IOException, GeneralSecurityException {

		Response response = new Response();

		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

		String range = sheetName + "!A1:Z";
		List<String> tickers = new ArrayList<>();
		tickers.add("Symbols");
		tickers.add(symbol);

		// Prepare the data to be written to the sheet
		List<List<Object>> data = new ArrayList<>();
		for (String ticker : tickers) {
			data.add(Collections.singletonList(ticker));
		}

		Sheets service = null;
		try {
			service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(KEY_FILE_LOCATION))
					.setApplicationName(APPLICATION_NAME).build();
		} catch (URISyntaxException | IOException | GeneralSecurityException e) {
			e.printStackTrace();
		}
		ValueRange body = new ValueRange().setValues(data);
		try {
			service.spreadsheets().values().update(spreadsheetId, range, body).setValueInputOption("RAW").execute();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			updateCellFormula(service, spreadsheetId, sheetName, 1, 2, formula);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ValueRange valueRange = service.spreadsheets().values().get(spreadsheetId, range).execute();
		List<List<Object>> values = valueRange.getValues();

		if (values == null || values.isEmpty()) {
			response.setStatus(false);
			response.setMessage("No data found");
			response.setStatusCode(HttpStatus.OK.value());
			response.setRetrievedResult(null);
			return response;
		} else {
			List<Object> headers = values.get(0);
			Map<String, Object> rowData = new LinkedHashMap<>(); // Create a single map to hold the data

			for (int colIndex = 0; colIndex < headers.size(); colIndex++) {
				String header = headers.get(colIndex).toString();
				Object value = (colIndex < values.get(1).size()) ? castToCorrectType(values.get(1).get(colIndex))
						: null;
				rowData.put(header, value);
			}
			response.setStatus(true);
			response.setMessage("Success");
			response.setStatusCode(HttpStatus.OK.value());
			response.setRetrievedResult(rowData);
			return response;
		}
	}

	private Object castToCorrectType(Object value) {
		if (value instanceof BigDecimal) {
			return ((BigDecimal) value).doubleValue();
		} else if (value instanceof BigInteger) {
			return ((BigInteger) value).longValue();
		} else {
			return value;
		}
	}

	public Response getSheetDataColumn(String formula, String sheetName) throws IOException {

		Response response = new Response();

		NetHttpTransport HTTP_TRANSPORT = null;
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		} catch (GeneralSecurityException | IOException e) {
			e.printStackTrace();
		}

		String range = sheetName + "!A2:Z";
		List<List<Object>> data = new ArrayList<>();
		Sheets service = null;
		try {
			service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(KEY_FILE_LOCATION))
					.setApplicationName(APPLICATION_NAME).build();
		} catch (IOException | GeneralSecurityException | URISyntaxException e) {
			e.printStackTrace();
		}
		ValueRange body = new ValueRange().setValues(data);
		try {
			service.spreadsheets().values().update(spreadsheetId, range, body).setValueInputOption("RAW").execute();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			updateCellFormula(service, spreadsheetId, sheetName, 1, 1, formula);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ValueRange valueRange = service.spreadsheets().values().get(spreadsheetId, range).execute();
		List<List<Object>> values = valueRange.getValues();

		if (values == null || values.isEmpty()) {
			response.setStatus(false);
			response.setMessage("No data found");
			response.setStatusCode(HttpStatus.OK.value());
			response.setRetrievedResult(null);
			return response;
		} else {
			List<Object> firstColumn = values.stream().map(row -> (row != null && !row.isEmpty()) ? row.get(0) : null)
					.collect(Collectors.toList());

			response.setStatus(true);
			response.setMessage("Success");
			response.setStatusCode(HttpStatus.OK.value());
			response.setRetrievedResult(IntStream.range(1, values.get(0).size()).mapToObj(colIndex -> {
				Map<String, Object> colData = new LinkedHashMap<>();
				for (int rowIndex = 0; rowIndex < values.size(); rowIndex++) {
					String header = firstColumn.get(rowIndex).toString();
					Object value = (colIndex < values.get(rowIndex).size())
							? castToCorrectType(values.get(rowIndex).get(colIndex))
							: null;
					colData.put(header, value);
				}
				return colData;
			}).collect(Collectors.toList()));
			return response;
		}
	}

	private static void updateCellFormula(Sheets sheetsService, String spreadsheetId, String sheetName, int row,
			int column, String formula) throws IOException {
		List<Request> requests = new ArrayList<>();
		List<CellData> cellDataList = new ArrayList<>();
		cellDataList.add(new CellData().setUserEnteredValue(new ExtendedValue().setFormulaValue(formula)));

		requests.add(new Request().setUpdateCells(new UpdateCellsRequest()
				.setStart(new GridCoordinate().setSheetId(getSheetId(sheetsService, spreadsheetId, sheetName))
						.setRowIndex(row - 1).setColumnIndex(column - 1))
				.setRows(Collections.singletonList(new RowData().setValues(cellDataList)))
				.setFields("userEnteredValue")));

		BatchUpdateSpreadsheetRequest batchUpdateRequest = new BatchUpdateSpreadsheetRequest().setRequests(requests);

		sheetsService.spreadsheets().batchUpdate(spreadsheetId, batchUpdateRequest).execute();
	}

	private static int getSheetId(Sheets sheetsService, String spreadsheetId, String sheetName) throws IOException {
		Spreadsheet spreadsheet = sheetsService.spreadsheets().get(spreadsheetId).execute();
		List<Sheet> sheets = spreadsheet.getSheets();

		for (Sheet sheet : sheets) {
			if (sheet.getProperties().getTitle().equals(sheetName)) {
				return sheet.getProperties().getSheetId();
			}
		}

		throw new IllegalArgumentException("Sheet not found: " + sheetName);
	}

	public Response getSheetDataTimeSeries(String formula, String sheetName)
			throws IOException, GeneralSecurityException {

		Response response = new Response();

		NetHttpTransport HTTP_TRANSPORT = null;
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		} catch (GeneralSecurityException | IOException e) {
			e.printStackTrace();
		}

		String range = sheetName + "!A2:Z";
		List<List<Object>> data = new ArrayList<>();
		Sheets service = null;
		try {
			service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(KEY_FILE_LOCATION))
					.setApplicationName(APPLICATION_NAME).build();
		} catch (IOException | GeneralSecurityException | URISyntaxException e) {
			e.printStackTrace();
		}
		ValueRange body = new ValueRange().setValues(data);
		try {
			service.spreadsheets().values().update(spreadsheetId, range, body).setValueInputOption("RAW").execute();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			updateCellFormula(service, spreadsheetId, sheetName, 1, 1, formula);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ValueRange valueRange = service.spreadsheets().values().get(spreadsheetId, range).execute();
		List<List<Object>> values = valueRange.getValues();

		if (values == null || values.isEmpty()) {
			response.setStatus(false);
			response.setMessage("No data found");
			response.setStatusCode(HttpStatus.OK.value());
			response.setRetrievedResult(null);
			return response;
		} else {
			List<Object> headers = values.get(0);

			response.setStatus(true);
			response.setMessage("Success");
			response.setStatusCode(HttpStatus.OK.value());
			response.setRetrievedResult(values.subList(1, values.size()).stream().map(row -> {
				Map<String, Object> rowData = new LinkedHashMap<>();
				for (int colIndex = 0; colIndex < headers.size(); colIndex++) {
					String header = headers.get(colIndex).toString();
					Object value = (colIndex < row.size()) ? castToCorrectType(row.get(colIndex)) : null;
					rowData.put(header, value);
				}
				return rowData;
			}).collect(Collectors.toList()));
			return response;
		}
	}

	public Response symbolSearch(String name) {
		Response response = new Response();
		SymbolSearchResponse symbolSearchResponse = new SymbolSearchResponse();

		List<BursaSymbolSearchResponse> bursaSearchSymbolResponseLists = new ArrayList<>();
		List<BursaSymbols> bursaSymbols = new ArrayList<>();
		bursaSymbols = bursaSymbolsRepository.getSymbols(name);
		for (BursaSymbols symbols : bursaSymbols) {
			BursaSymbolSearchResponse bursaSearchSymbolResponseList = new BursaSymbolSearchResponse();
			bursaSearchSymbolResponseList.setName(symbols.getCompanyName());
			bursaSearchSymbolResponseList.setSymbol(symbols.getSymbol());
			bursaSearchSymbolResponseList.setS3key(null);
			bursaSearchSymbolResponseLists.add(bursaSearchSymbolResponseList);
		}

		List<UsSymbolSearchResponse> usSearchSymbolResponseLists = new ArrayList<>();
		List<UsSymbols> usSymbols = new ArrayList<>();
		usSymbols = usSymbolsRepository.getSymbols(name);
		for (UsSymbols symbols : usSymbols) {
			UsSymbolSearchResponse usSearchSymbolResponseList = new UsSymbolSearchResponse();
			usSearchSymbolResponseList.setName(symbols.getCompanyName());
			usSearchSymbolResponseList.setSymbol(symbols.getSymbol());
			String url = getPresignedUrl("Bursa/" + symbols.getS3Key(), 10);
			if (url.isEmpty()) {
				usSearchSymbolResponseList.setS3key(null);
			} else {
				usSearchSymbolResponseList.setS3key(url);
			}
			usSearchSymbolResponseLists.add(usSearchSymbolResponseList);
		}
		symbolSearchResponse.setBursaSearchSymbolResponseList(bursaSearchSymbolResponseLists);
		symbolSearchResponse.setUsSymbolSearchResponseList(usSearchSymbolResponseLists);

		response.setStatus(true);
		response.setMessage("Success");
		response.setStatusCode(HttpStatus.OK.value());
		response.setRetrievedResult(symbolSearchResponse);
		return response;
	}

	public String getPresignedUrl(String path, int duration) {

		S3Presigner s3Client = S3Presigner.builder().region(region).credentialsProvider(getCredentials()).build();

		GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucketName).key(path).build();

		GetObjectPresignRequest request = GetObjectPresignRequest.builder()
				.signatureDuration(Duration.ofMinutes(duration)).getObjectRequest(getObjectRequest).build();

		PresignedGetObjectRequest getObjectRequest2 = s3Client.presignGetObject(request);

		return getObjectRequest2.url().toString();
	}

	private StaticCredentialsProvider getCredentials() {
		AwsBasicCredentials sessionCredentials = AwsBasicCredentials.create(accessKey, secretkey);

		return StaticCredentialsProvider.create(sessionCredentials);
	}
	
	public Response checkBoolean(String symbol) {
		Response response = new Response();
		BooleanResponse booleanResponse = new BooleanResponse();
		
		Optional<BursaSymbols> optionalBursa = bursaSymbolsRepository.getSymbol(symbol);
		if(optionalBursa.isEmpty()) {
			booleanResponse.setMalaysian(false);
			Optional<UsSymbols> optionalUs = usSymbolsRepository.findBySymbol(symbol);
			UsSymbols usSymbols = optionalUs.get();
			String url = getPresignedUrl("Bursa/" + usSymbols.getS3Key(), 10);
			booleanResponse.setS3Url(url);
			response.setStatus(true);
			response.setMessage("Success");
			response.setStatusCode(HttpStatus.OK.value());
			response.setRetrievedResult(booleanResponse);
			return response;
		}
		booleanResponse.setMalaysian(true);
		booleanResponse.setS3Url(null);
		response.setStatus(true);
		response.setMessage("Success");
		response.setStatusCode(HttpStatus.OK.value());
		response.setRetrievedResult(booleanResponse);
		return response;
	}

}
