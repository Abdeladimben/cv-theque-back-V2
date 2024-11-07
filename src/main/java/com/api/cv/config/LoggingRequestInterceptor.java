package com.api.cv.config;

import org.springframework.http.HttpRequest;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.ClientHttpRequestInterceptor;

import java.io.IOException;

public class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {
        // Log the request details
        System.out.println("Request: " + request.getMethod() + " " + request.getURI());
        System.out.println("Request Headers: " + request.getHeaders());
        System.out.println("Request Body: " + new String(body));

        // Proceed with the request and get the response
        ClientHttpResponse response = execution.execute(request, body);

        // Log the response details
        System.out.println("Response: " + response.getStatusCode() + " " + response.getStatusText());
        System.out.println("Response Headers: " + response.getHeaders());

        // Optionally, log the response body (be cautious with large bodies)
        return response;
    }
}