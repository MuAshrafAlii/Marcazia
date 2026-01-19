package com.marcazia.productService.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void initializeData() {
        log.info("Checking if initial data needs to be populated...");

        // Check if categories are empty
        if (categoryRepository.count() == 0) {
            log.info("No categories found. Populating initial categories...");
            populateCategories();
        }

        // Check if products are empty
        if (productRepository.count() == 0) {
            log.info("No products found. Populating initial products...");
            populateProducts();
        }

        log.info("Data initialization completed.");
    }

    private void populateCategories() {
        List<Category> categories = List.of(
                Category.builder()
                        .name("Electronics")
                        .description("Electronic devices and gadgets")
                        .build(),
                Category.builder()
                        .name("Clothing")
                        .description("Clothing and apparel")
                        .build(),
                Category.builder()
                        .name("Books")
                        .description("Books and publications")
                        .build(),
                Category.builder()
                        .name("Home & Garden")
                        .description("Home and garden products")
                        .build(),
                Category.builder()
                        .name("Sports & Outdoors")
                        .description("Sports and outdoor equipment")
                        .build()
        );

        categoryRepository.saveAll(categories);
        log.info("Successfully populated {} categories", categories.size());
    }

    private void populateProducts() {
        // Fetch all categories
        List<Category> categories = categoryRepository.findAll();

        if (categories.isEmpty()) {
            log.warn("No categories found. Cannot populate products without categories.");
            return;
        }

        List<Product> products = List.of(
                // Electronics
                Product.builder()
                        .name("Laptop")
                        .description("High-performance laptop with 16GB RAM")
                        .price(new BigDecimal("999.99"))
                        .availableQuantity(15)
                        .category(categories.get(0))
                        .build(),
                Product.builder()
                        .name("Smartphone")
                        .description("Latest smartphone with advanced features")
                        .price(new BigDecimal("599.99"))
                        .availableQuantity(30)
                        .category(categories.get(0))
                        .build(),
                Product.builder()
                        .name("Wireless Headphones")
                        .description("Noise-cancelling wireless headphones")
                        .price(new BigDecimal("149.99"))
                        .availableQuantity(50)
                        .category(categories.get(0))
                        .build(),

                // Clothing
                Product.builder()
                        .name("T-Shirt")
                        .description("Comfortable cotton t-shirt")
                        .price(new BigDecimal("29.99"))
                        .availableQuantity(100)
                        .category(categories.get(1))
                        .build(),
                Product.builder()
                        .name("Jeans")
                        .description("Classic blue jeans")
                        .price(new BigDecimal("79.99"))
                        .availableQuantity(60)
                        .category(categories.get(1))
                        .build(),
                Product.builder()
                        .name("Jacket")
                        .description("Winter jacket with thermal lining")
                        .price(new BigDecimal("129.99"))
                        .availableQuantity(25)
                        .category(categories.get(1))
                        .build(),

                // Books
                Product.builder()
                        .name("The Great Gatsby")
                        .description("Classic novel by F. Scott Fitzgerald")
                        .price(new BigDecimal("14.99"))
                        .availableQuantity(45)
                        .category(categories.get(2))
                        .build(),
                Product.builder()
                        .name("To Kill a Mockingbird")
                        .description("Pulitzer Prize-winning novel")
                        .price(new BigDecimal("16.99"))
                        .availableQuantity(35)
                        .category(categories.get(2))
                        .build(),
                Product.builder()
                        .name("1984")
                        .description("Dystopian novel by George Orwell")
                        .price(new BigDecimal("13.99"))
                        .availableQuantity(55)
                        .category(categories.get(2))
                        .build(),

                // Home & Garden
                Product.builder()
                        .name("Coffee Maker")
                        .description("Automatic coffee maker with timer")
                        .price(new BigDecimal("89.99"))
                        .availableQuantity(20)
                        .category(categories.get(3))
                        .build(),
                Product.builder()
                        .name("Desk Lamp")
                        .description("LED desk lamp with adjustable brightness")
                        .price(new BigDecimal("39.99"))
                        .availableQuantity(40)
                        .category(categories.get(3))
                        .build(),
                Product.builder()
                        .name("Plant Pot")
                        .description("Decorative ceramic plant pot")
                        .price(new BigDecimal("24.99"))
                        .availableQuantity(80)
                        .category(categories.get(3))
                        .build(),

                // Sports & Outdoors
                Product.builder()
                        .name("Running Shoes")
                        .description("Professional running shoes with cushioning")
                        .price(new BigDecimal("119.99"))
                        .availableQuantity(40)
                        .category(categories.get(4))
                        .build(),
                Product.builder()
                        .name("Yoga Mat")
                        .description("Non-slip yoga mat with carrying strap")
                        .price(new BigDecimal("34.99"))
                        .availableQuantity(65)
                        .category(categories.get(4))
                        .build(),
                Product.builder()
                        .name("Bicycle Helmet")
                        .description("Safety helmet for cycling")
                        .price(new BigDecimal("54.99"))
                        .availableQuantity(50)
                        .category(categories.get(4))
                        .build()
        );

        productRepository.saveAll(products);
        log.info("Successfully populated {} products", products.size());
    }
}

