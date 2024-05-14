import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.CurrencyDetails;
import models.CurrencyRecord;
import models.CurrencyResult;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class QueryCurrency {
    static CurrencyResult doCurrencyConversion(int option, double amount){

        Gson gson = new GsonBuilder()
                .create();

        CurrencyDetails usd = new CurrencyDetails("United States", "USD", '$');
        CurrencyDetails ars = new CurrencyDetails("Argentina", "ARS", '$');
        CurrencyDetails mxn = new CurrencyDetails("Mexico", "MXN", '$');
        CurrencyDetails eur = new CurrencyDetails("Eurozone", "EUR", '€');

        CurrencyDetails from = null;
        CurrencyDetails to = null;

        if(option == 1){
            from = usd;
            to = ars;
        }if(option == 2){
            from = ars;
            to = usd;
        }if(option == 3){
            from = usd;
            to = mxn;
        }if(option == 4){
            from = mxn;
            to = usd;
        }if(option == 5){
            from = usd;
            to = eur;
        }if(option == 6){
            from = eur;
            to = usd;
        }

        String apiUrl = "https://v6.exchangerate-api.com/v6/9358070af0b957a3a3f4b4ff/pair/"+ from.getCurrency() +"/"+ to.getCurrency() +"/" + amount;

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String json = response.body();

            CurrencyResult currencyResult = new CurrencyResult(gson.fromJson(json, CurrencyRecord.class), from, to);
            return currencyResult;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
