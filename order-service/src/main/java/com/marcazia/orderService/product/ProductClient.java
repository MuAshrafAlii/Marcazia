package com.marcazia.orderService.product;

import com.marcazia.orderService.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Component
@RequiredArgsConstructor
public class ProductClient {
    @Value("${application.config.product-url}")
    private String productUrl;

    private final RestTemplate restTemplate;

    public List<PurchaseResponse> purchaseProducts(
            List<PurchaseRequest> requestBody
    ) {
        // Create HTTP headers object to configure the request
        HttpHeaders headers = new HttpHeaders();
        // Set the content type to JSON so the server knows we're sending JSON data
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Wrap the request body (list of purchase requests) and headers into an HttpEntity
        // This entity will be sent as the request body to the server
        HttpEntity<List<PurchaseRequest>> requestEntity =
                new HttpEntity<>(requestBody, headers);

        // Define the response type as a parameterized list of PurchaseResponse objects
        // This tells RestTemplate how to deserialize the response JSON into Java objects
        ParameterizedTypeReference<List<PurchaseResponse>> responseType =
                new ParameterizedTypeReference<>() {};

        // Send a POST request to the product service's "/purchase" endpoint
        // Pass the request entity, expect back a list of PurchaseResponse objects
        // The exchange method sends the HTTP request and receives the response
        ResponseEntity<List<PurchaseResponse>> responseEntity =
                restTemplate.exchange(
                        productUrl + "/purchase",  // Full URL to product service endpoint
                        HttpMethod.POST,            // HTTP method (POST for creating/processing data)
                        requestEntity,              // Request with body and headers
                        responseType                // Expected response type
                );

        // Check if the HTTP response status code indicates an error (4xx or 5xx)
        if (responseEntity.getStatusCode().isError()) {
            // If there was an error, throw a custom BusinessException with details
            throw new BusinessException(
                    "An error occurred while processing the product purchase: "
                            + responseEntity.getStatusCode()  // Include the error status code in message
            );
        }

        // If request was successful, extract and return the response body (list of purchase responses)
        return responseEntity.getBody();
    }

}
