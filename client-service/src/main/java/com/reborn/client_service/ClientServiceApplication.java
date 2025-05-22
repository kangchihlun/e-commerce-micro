package com.reborn.client_service;

import com.reborn.client_service.config.ServiceConfig;
import com.reborn.client_service.dto.LoginRequest;
import com.reborn.client_service.dto.SignupRequest;
import com.reborn.client_service.dto.SignupResponse;
import com.reborn.client_service.model.Product;
import com.reborn.client_service.model.User;
import com.reborn.client_service.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ClientServiceApplication {

    @Autowired
    private ServiceConfig serviceConfig;

    @Autowired
    private RestTemplate restTemplate;

    public static void main(String[] args) {
        SpringApplication.run(ClientServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner run() {
        return args -> {
            // 1. Signup
            System.out.println("\n1. Calling signup...");
            String signupUrl = serviceConfig.getAccountServiceUrl() + "/api/users/signup";
            SignupRequest signupRequest = new SignupRequest("test@example.com", "password123", "Test User");
            SignupResponse signupResponse = restTemplate.postForObject(signupUrl, signupRequest, SignupResponse.class);
            
            if (!signupResponse.isSuccess()) {
                System.out.println("Signup failed: " + signupResponse.getMessage());
                // If user already exists, we can proceed with login
                if (signupResponse.getMessage().equals("Email already exists")) {
                    System.out.println("User already exists, proceeding with login...");
                } else {
                    // For other errors, we should stop
                    System.out.println("Unexpected error during signup, stopping...");
                    return;
                }
            } else {
                System.out.println("Signup successful: " + signupResponse.getUser());
            }

            // 2. Signin
            System.out.println("\n2. Calling signin...");
            String signinUrl = serviceConfig.getAccountServiceUrl() + "/api/users/login";
            LoginRequest loginRequest = new LoginRequest("test@example.com", "password123");
            User loginResponse = restTemplate.postForObject(signinUrl, loginRequest, User.class);
            System.out.println("Login response: " + loginResponse);

            // 3. Create a new product
            System.out.println("\n3. Creating a new product...");
            String createProductUrl = serviceConfig.getProductServiceUrl() + "/api/products";
            Product newProduct = new Product();
            newProduct.setName("Test Product");
            newProduct.setDescription("This is a test product");
            newProduct.setPrice(99.99);
            newProduct.setAccountId(loginResponse.getId());
            Product createdProduct = restTemplate.postForObject(createProductUrl, newProduct, Product.class);
            System.out.println("Created product: " + createdProduct);

            // 4. Get first product
            System.out.println("\n4. Getting first product...");
            String getProductUrl = serviceConfig.getProductServiceUrl() + "/api/products/1";
            Product product1 = restTemplate.getForObject(getProductUrl, Product.class);
            System.out.println("First product: " + product1);

            // 5. Get all products
            System.out.println("\n5. Getting all products...");
            String getProductsUrl = serviceConfig.getProductServiceUrl() + "/api/products";
            Product[] products = restTemplate.getForObject(getProductsUrl, Product[].class);
            System.out.println("All products: " + java.util.Arrays.toString(products));

            // 6. Update product
            System.out.println("\n6. Updating product...");
            String updateProductUrl = serviceConfig.getProductServiceUrl() + "/api/products/1";
            Product updateRequest = new Product();
            updateRequest.setName("Updated Product");
            updateRequest.setDescription("Updated Description");
            updateRequest.setPrice(199.99);
            updateRequest.setAccountId(loginResponse.getId());
            restTemplate.put(updateProductUrl, updateRequest);
            System.out.println("Updated product: " + updateRequest);

            // 7. Create order
            System.out.println("\n7. Creating order...");
            String createOrderUrl = serviceConfig.getOrderServiceUrl() + "/api/orders";
            Order newOrder = new Order();
            newOrder.setAccountId(loginResponse.getId());
            newOrder.setStatus("PENDING");
            newOrder.setTotalAmount(199.99); // Using the updated product price
            Order createdOrder = restTemplate.postForObject(createOrderUrl, newOrder, Order.class);
            System.out.println("Created order: " + createdOrder);

            // 8. Get order by ID
            System.out.println("\n8. Getting order by ID...");
            String getOrderUrl = serviceConfig.getOrderServiceUrl() + "/api/orders/" + createdOrder.getId();
            Order retrievedOrder = restTemplate.getForObject(getOrderUrl, Order.class);
            System.out.println("Retrieved order: " + retrievedOrder);

            // 9. Get orders by account
            System.out.println("\n9. Getting orders by account...");
            String getOrdersByAccountUrl = serviceConfig.getOrderServiceUrl() + "/api/orders/account/" + loginResponse.getId();
            Order[] accountOrders = restTemplate.getForObject(getOrdersByAccountUrl, Order[].class);
            System.out.println("Account orders: " + java.util.Arrays.toString(accountOrders));
        };
    }
}