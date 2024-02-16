package com.bursa.sheetIntegration.serviceimpl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bursa.sheetIntegration.service.GoogleSheetsService;
import com.bursa.sheetIntegration.sheetconfig.GoogleSheetsConfig;
import com.google.api.services.sheets.v4.Sheets;
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

@Service
public class GoogleSheetsServiceImpl implements GoogleSheetsService {

	@Autowired
	private Sheets sheetsService;

	@Autowired
	private GoogleSheetsConfig config;

//	@Value("${google.sheet.spreadsheet-id}")
	private String spreadsheetId = "1ZM8zw-y8aQEAfcBaOP4FyooTE6d-Yey8AvCHkwVBO04";

	private static final String TOKENS_DIRECTORY_PATH = "tokens";

	public List<Map<String, Object>> getSheetData(String symbol, String type)
			throws IOException, GeneralSecurityException {
		String range = "Sheet1!A1:Z";
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
			service = config.sheetsService();
		} catch (IOException | GeneralSecurityException e) {
			e.printStackTrace();
		}
		ValueRange body = new ValueRange().setValues(data);
		try {
			service.spreadsheets().values().update(spreadsheetId, range, body).setValueInputOption("RAW").execute();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String formula = "=SF(\"" + symbol + "\",\"" + type + "\",\"all\")";
		try {
			updateCellFormula(service, spreadsheetId, "Sheet1", 1, 2, formula);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ValueRange response = sheetsService.spreadsheets().values().get(spreadsheetId, range).execute();
		List<List<Object>> values = response.getValues();

		if (values == null || values.isEmpty()) {
			return new ArrayList<>();
		} else {
			List<Object> headers = values.get(0);

			return values.subList(1, values.size()).stream().map(row -> {
				Map<String, Object> rowData = new LinkedHashMap<>();
				for (int colIndex = 0; colIndex < headers.size(); colIndex++) {
					String header = headers.get(colIndex).toString();
					Object value = (colIndex < row.size()) ? castToCorrectType(row.get(colIndex)) : null;
					rowData.put(header, value);
				}
				return rowData;
			}).collect(Collectors.toList());
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

	public List<Map<String, Object>> getSheetDataColumn(String symbol, String type, boolean report, String year)
			throws IOException {
		String range = "Sheet2!A2:Z";
		List<List<Object>> data = new ArrayList<>();
		Sheets service = null;
		try {
			service = config.sheetsService();
		} catch (IOException | GeneralSecurityException e) {
			e.printStackTrace();
		}
		ValueRange body = new ValueRange().setValues(data);
		try {
			service.spreadsheets().values().update(spreadsheetId, range, body).setValueInputOption("RAW").execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String formula;
		if (report == false && year == null) {
			formula = "=SF(\"" + symbol + "\",\"" + type + "\",\"all\")";
		} else if (report == true && year == null) {
			formula = "Invalid Request";
		} else if (report == false && year != null) {
			formula = "=SF(\"" + symbol + "\",\"" + type + "\",\"all\",\"" + year + "\")";
		} else {
			formula = "=SF(\"" + symbol + "\",\"" + type + "Q\",\"all\",\"" + year + "\")";
		}
		try {
			updateCellFormula(service, spreadsheetId, "Sheet2", 1, 1, formula);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ValueRange response = sheetsService.spreadsheets().values().get(spreadsheetId, range).execute();
		List<List<Object>> values = response.getValues();

		if (values == null || values.isEmpty()) {
			return new ArrayList<>();
		} else {
			List<Object> firstColumn = values.stream().map(row -> (row != null && !row.isEmpty()) ? row.get(0) : null)
					.collect(Collectors.toList());

			return IntStream.range(1, values.get(0).size()).mapToObj(colIndex -> {
				Map<String, Object> colData = new LinkedHashMap<>();
				for (int rowIndex = 0; rowIndex < values.size(); rowIndex++) {
					String header = firstColumn.get(rowIndex).toString();
					Object value = (colIndex < values.get(rowIndex).size())
							? castToCorrectType(values.get(rowIndex).get(colIndex))
							: null;
					colData.put(header, value);
				}
				return colData;
			}).collect(Collectors.toList());
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

}
