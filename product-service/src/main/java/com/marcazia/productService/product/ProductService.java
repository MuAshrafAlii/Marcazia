package com.marcazia.productService.product;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;
    private final ProductMapper mapper;

    public Long createProduct(ProductRequest request) {
        Product product = mapper.toProduct(request);
        return repository.save(product).getId();
    }

    public ProductResponse findById(Long productId) {
        return repository.findById(productId)
                .map(mapper::toProductResponse)
                .orElseThrow(() ->
                        new EntityNotFoundException("Product not found with ID " + productId)
                );
    }

    public List<ProductResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toProductResponse)
                .collect(Collectors.toList());
    }

    public List<ProductPurchaseResponse> purchaseProducts(
            @Valid List<ProductPurchaseRequest> request
    ) {
        List<Long> productIds = request.stream()
                .map(ProductPurchaseRequest::productId)
                .toList();
        List<Product> storedProducts = repository.findAllByIdInOrderById(productIds);

        if (productIds.size() != storedProducts.size()) {
            throw new ProductPurchaseException(
                    "One or more products do not exist"
            );
        }


    }
