package com.api.cv.config;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.ClientHttpRequestInterceptor;

import java.io.IOException;
import java.util.logging.Logger;

public class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger LOGGER = Logger.getLogger(LoggingRequestInterceptor.class.getName());
    private static final String SEPARATOR = "------------------------------";

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {
        logRequest(request, body);

        ClientHttpResponse response = execution.execute(request, body);

        logResponse(response);

        return response;
    }

    private void logRequest(HttpRequest request, byte[] body) {
        LOGGER.info(SEPARATOR);
        LOGGER.info("Request: " + request.getMethod() + " " + request.getURI());
        LOGGER.info("Request Headers: " + request.getHeaders());
        LOGGER.info("Request Body: " + new String(body));
        LOGGER.info(SEPARATOR);
    }

    private void logResponse(ClientHttpResponse response) throws IOException {
        LOGGER.info(SEPARATOR);
        LOGGER.info("Response: " + response.getStatusCode() + " " + response.getStatusText());
        LOGGER.info("Response Headers: " + response.getHeaders());
        LOGGER.info(SEPARATOR);
    }
}
