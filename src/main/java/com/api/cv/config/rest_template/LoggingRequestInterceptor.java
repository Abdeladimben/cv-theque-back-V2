package com.api.cv.config.rest_template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {

    private String endpoint = "";

    /**
     * The Logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(LoggingRequestInterceptor.class);

    public LoggingRequestInterceptor() {
    }

    public LoggingRequestInterceptor(String endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        traceRequest(request, body);
        final long begin = System.currentTimeMillis();
        ClientHttpResponse response = execution.execute(request, body);
        final long duration = System.currentTimeMillis() - begin;
        traceResponse(request, response, duration);
        return response;
    }

    /**
     *
     * Methode traceRequest : Log request.
     *
     * @param request
     * @param body
     */
    private void traceRequest(HttpRequest request, byte[] body) {
        if (LOG.isInfoEnabled()) {
            LOG.info("=========================== {} rest api request begins ================================================", endpoint);
            LOG.info("URI         : {}", request.getURI());
            LOG.info("Method      : {}", request.getMethod());
            LOG.info("Headers     : {}", request.getHeaders());
            LOG.info("Request body: {}", new String(body, StandardCharsets.UTF_8));
            LOG.info("=========================== {} rest api request end ================================================", endpoint);
        }
    }

    /**
     *
     * Methode traceResponse : Log response.
     *
     * @param request
     * @param response
     * @param duration
     * @throws IOException
     */
    private void traceResponse(HttpRequest request, ClientHttpResponse response, final long duration)
            throws IOException {
        if (LOG.isInfoEnabled()) {
            StringBuilder inputStringBuilder = new StringBuilder();
            if (response.getHeaders().getContentType() != null
                    && !response.getHeaders().getContentType().isCompatibleWith(MediaType.APPLICATION_PDF)
                    && !response.getHeaders().getContentType().isCompatibleWith(MediaType.APPLICATION_OCTET_STREAM)) {
                try (BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(response.getBody(), StandardCharsets.UTF_8))) {
                    bufferedReader.lines().filter(Objects::nonNull)
                            .forEach(s -> inputStringBuilder.append(s).append(System.lineSeparator()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            LOG.info("============================ {} rest api response begins ==========================================", endpoint);
            LOG.info("Status code  : {}", response.getStatusCode());
            LOG.info("Status text  : {}", response.getStatusText());
            LOG.info("Headers      : {}", response.getHeaders());
            LOG.info("Duration (ms) of Api ({}) \"{}\" = {}", request.getMethod(), request.getURI().getPath(), duration);
            LOG.info("Response body: {}", inputStringBuilder);
            LOG.info("============================ {} rest api response end =================================================", endpoint);
        }
    }

}
