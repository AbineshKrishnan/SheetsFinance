package com.bursa.sheetIntegration.sheetconfig;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bursa.sheetIntegration.config.GoogleOAuthProperties;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;

@Configuration
public class GoogleSheetsConfig {

	private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	private static final String TOKENS_DIRECTORY_PATH = "tokens";
	private final String clientId = "335409018004-frv901o8cghspehfb45ncu5d057keuod.apps.googleusercontent.com";
	private final String clientSecret = "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";

//	@Autowired
//	private GoogleOAuthProperties googleOAuthProperties;

//	@Value("${google.sheet.spreadsheet-id}")
//	private String spreadsheetId = "1ZM8zw-y8aQEAfcBaOP4FyooTE6d-Yey8AvCHkwVBO04";

	@Bean
	public Sheets sheetsService() throws IOException, GeneralSecurityException {
		deleteTokensDirectory();
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT, clientId,clientSecret))
				.setApplicationName(APPLICATION_NAME).build();
	}

//	private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
////		InputStream in = getClass().getResourceAsStream("/credentials.json");
////		if (in == null) {
////			throw new IOException("Resource not found: /credentials.json");
////		}
//		List<String> list = new ArrayList<>();
//		list.add("http://localhost:8821/Callback");
////		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
//		GoogleClientSecrets clientSecrets = new GoogleClientSecrets();
//		clientSecrets.getWeb().setClientId("335409018004-frv901o8cghspehfb45ncu5d057keuod.apps.googleusercontent.com");
//		clientSecrets.getWeb().setAuthUri("https://accounts.google.com/o/oauth2/auth");
//		clientSecrets.getWeb().setTokenUri("https://oauth2.googleapis.com/token");
//		clientSecrets.getWeb().setClientSecret("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");
//		clientSecrets.getWeb().setRedirectUris(list);
//
//		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
//				clientSecrets, Collections.singletonList(SheetsScopes.SPREADSHEETS))
//				.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
//				.setAccessType("offline").build();
//
//		LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8821).build();
//		return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
//	}
	
//	 private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT, String clientId, String clientSecret) throws IOException {
//	        GoogleClientSecrets clientSecrets = new GoogleClientSecrets();
//	        clientSecrets.setInstalled(new GoogleClientSecrets.Details()
//	                .setClientId(clientId)
//	                .setClientSecret(clientSecret));
//
//	        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
//	                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets,
//	                Collections.singletonList(SheetsScopes.SPREADSHEETS))
//	                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
//	                .setAccessType("offline")
//	                .build();
//
//	        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setHost("api.testbursa.mywire.org").setPort(8821).build();
//	        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
//	    }
	
	private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT, String clientId, String clientSecret) throws IOException {
	    GoogleClientSecrets clientSecrets = new GoogleClientSecrets();
	    clientSecrets.setInstalled(new GoogleClientSecrets.Details()
	            .setClientId(clientId)
	            .setClientSecret(clientSecret));

	    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
	            HTTP_TRANSPORT, JSON_FACTORY, clientSecrets,
	            Collections.singletonList(SheetsScopes.SPREADSHEETS))
	            .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
	            .setAccessType("offline")
	            .build();

	    // Define your server's redirect URI where Google will redirect after authorization
	    String redirectUri = "http://localhost:8821/callback"; // Update this with your actual domain and callback path

	    // Create an authorization URL with the specified redirect URI
	    AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
	    authorizationUrl.setRedirectUri(redirectUri);
	    String authorizationUrlString = authorizationUrl.build();

	    // Print out the authorization URL and have the user visit it to authorize the application
	    System.out.println("Authorization URL: " + authorizationUrlString);

	    // After the user authorizes the application, they will be redirected to the specified redirect URI
	    // You need to handle the authorization response in your server code

	    return null; // Return null for now; you need to handle the authorization flow in your server code
	}


	private static void deleteTokensDirectory() {
		try {
			File tokensDirectory = new File(TOKENS_DIRECTORY_PATH);
			if (tokensDirectory.exists()) {
				deleteRecursive(tokensDirectory);
				System.out.println("Tokens directory deleted.");
			} else {
				System.out.println("Tokens directory not found.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void deleteRecursive(File file) {
		if (file.isDirectory()) {
			for (File subFile : file.listFiles()) {
				deleteRecursive(subFile);
			}
		}
		file.delete();
	}

}
