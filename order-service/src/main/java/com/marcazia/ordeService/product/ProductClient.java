package com.marcazia.ordeService.product;

import com.marcazia.ordeService.exception.BusinessException;
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
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<PurchaseRequest>> requestEntity =
                new HttpEntity<>(requestBody, headers);

        ParameterizedTypeReference<List<PurchaseResponse>> responseType =
                new ParameterizedTypeReference<>() {};

        ResponseEntity<List<PurchaseResponse>> responseEntity =
                restTemplate.exchange(
                        productUrl + "/purchase",
                        HttpMethod.POST,
                        requestEntity,
                        responseType
                );

        if (responseEntity.getStatusCode().isError()) {
            throw new BusinessException(
                    "An error occurred while processing the product purchase: "
                            + responseEntity.getStatusCode()
            );
        }

        return responseEntity.getBody();
    }

}
