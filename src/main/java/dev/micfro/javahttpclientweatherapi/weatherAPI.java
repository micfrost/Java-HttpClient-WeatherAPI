package dev.micfro.javahttpclientweatherapi;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class weatherAPI {
    public static void main(String[] args) {
        // Define the city for which to fetch the weather forecast
        String zip = "04229";

        // Fetch the weather forecast for the specified city
        fetchWeatherForecast(zip);
    }

    // Method to fetch the weather forecast for a specific city
    private static void fetchWeatherForecast(String theZip) {
        // Define the URL of the weather API endpoint
        String baseUrl = "http://api.openweathermap.org/data/2.5/weather";
        String apiKey = Config.getApiKey();
        String units = "metric";
        String zip = theZip;
        String Country = "de";

        String url = baseUrl + "?zip=" + zip + "," + Country + "&units=" + units + "&appid=" + apiKey;

        //  for city
        //  String city = "Leipzig";
        //  String url = baseUrl + "?q=" + city + "&units=" + units + "&appid=" + apiKey;


        // Create an instance of HttpClient
        HttpClient httpClient = HttpClient.newHttpClient();

        // Create an instance of HttpRequest with the URL
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET() // We're sending a GET request
                .build();

        try {
            // Send the HTTP request and retrieve the response
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            System.out.println("Requesting weather data from: " + baseUrl);

            // Check if the request was successful (status code 200)
            if (httpResponse.statusCode() == 200) {
                // Parse JSON response and extract relevant weather information
                JSONObject json = new JSONObject(httpResponse.body());
                JSONObject main = json.getJSONObject("main");
                String city = json.getString("name");
                double temperature = main.getDouble("temp");
                double feelslike = main.getDouble("feels_like");
                double humidity = main.getDouble("humidity");
                double windSpeed = json.getJSONObject("wind").getDouble("speed");
                String weatherDescription = json.getJSONArray("weather").getJSONObject(0).getString("description");

                // Display the weather forecast
                System.out.println("- - - ");
                System.out.println("Weather forecast for provided zip " + theZip + " is for " + city + ": ");

                System.out.println("- Temperature: " + temperature + "°C"); // Temperature is in Celsius because of the units parameter as metric
                System.out.println("- Feels like: " + feelslike + "°C");

                System.out.println("- Humidity: " + humidity + "%");
                System.out.println("- Wind Speed: " + windSpeed + " m/s");
                System.out.println("- Weather Description: " + weatherDescription);
                System.out.println("- - - ");
            } else {
                // If the request was not successful, print the error message
                System.out.println("Error: " + httpResponse.statusCode() + " - " + httpResponse.body());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

