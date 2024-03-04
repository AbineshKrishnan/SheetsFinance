package com.bursa.sheetIntegration.serviceimpl;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.bursa.sheetIntegration.entity.UsSymbols;
import com.bursa.sheetIntegration.repository.UsSymbolsRepository;
import com.bursa.sheetIntegration.response.Response;
import com.bursa.sheetIntegration.response.YahooTrendingTickersResponse;
import com.bursa.sheetIntegration.response.YahooTrendingTickersResponse.TrendingTickersFinance;
import com.bursa.sheetIntegration.service.YahooService;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

@Service
public class YahooServiceImpl implements YahooService {

    private String trendingTickersApiUri = "https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/get-trending-tickers?region=US";

    private String yahooRapidApiKey = "dea2cb85e9mshd7ca5e0ca875837p16ed83jsnaacac1bdc32e";

    private String yahooRapidApiHost = "apidojo-yahoo-finance-v1.p.rapidapi.com";

    private String accessKey = "AKIAXYKJWNQTB4PFIC2A";

    private String secretkey = "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";

    private Region region = Region.AP_SOUTH_1;

    private String bucketName = "jahirs3";

    @Autowired
    private UsSymbolsRepository usSymbolsRepository;

    public Response getTrendingTickers(String region) {
    	Response response = new Response();
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(trendingTickersApiUri))
                    .header("X-RapidAPI-Key", yahooRapidApiKey).header("X-RapidAPI-Host", yahooRapidApiHost)
                    .method("GET", HttpRequest.BodyPublishers.noBody()).build();
            HttpResponse<String> httpResponse = HttpClient.newHttpClient().send(httpRequest,
                    HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            YahooTrendingTickersResponse tickersResponse = objectMapper.readValue(httpResponse.body(),
                    YahooTrendingTickersResponse.class);
            int count = 1;
            for (TrendingTickersFinance.TrendingTickersQuote result : tickersResponse.getFinance().getResult().get(0)
                    .getQuotes()) {

                count++;

                Optional<UsSymbols> usSymbolsOpt = usSymbolsRepository.findBySymbol(result.getSymbol());
                UsSymbols symbols = new UsSymbols();
                if (usSymbolsOpt.isPresent()) {
                    symbols = usSymbolsOpt.get();

                    String preSignedUrl = getPresignedUrl("Bursa/" + symbols.getS3Key(), 10);
                    if (null != preSignedUrl && !preSignedUrl.isBlank()) {
                        result.setImageUrl(preSignedUrl);
                    }
                } else {
                    result.setImageUrl(null);
                }
                if (count == 6) {
                    break;
                }

            }
//            s3Response.setYahooTrendingTickersResponse(tickersResponse);
//            return s3Response;
            response.setStatus(true);
			response.setMessage("Success");
			response.setStatusCode(HttpStatus.OK.value());
			response.setRetrievedResult(tickersResponse);
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

}