package com.reborn.client_service;

import com.reborn.client_service.config.ServiceConfig;
import com.reborn.client_service.dto.LoginRequest;
import com.reborn.client_service.dto.SignupRequest;
import com.reborn.client_service.dto.SignupResponse;
import com.reborn.client_service.dto.BasicReq;
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

    public void UserClientProcess() {
        
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
    
    }

    public void ExpenseClientProcess() {
        System.out.println("\n=== Expense Service Test Process ===");
        
        // 1. Create mock expense data with 3 ExpData items
        System.out.println("\n1. Creating mock expense data...");
        BasicReq basicReq = new BasicReq();
        basicReq.setFileNo("EXP2024001");
        basicReq.setFillFinDep("資訊部");
        basicReq.setFillId("USER001");
        basicReq.setBudgetYear(2024);
        basicReq.setApproveId("MANAGER001");
        
        // Create 3 ExpData items
        java.util.List<BasicReq.ExpData> expDataList = new java.util.ArrayList<>();
        
        // ExpData 1: 交通費
        BasicReq.ExpData expData1 = new BasicReq.ExpData();
        expData1.setReimburseDetailId("DETAIL001");
        expData1.setExpCategory("TRAVEL");
        expData1.setExpDetail("台北到高雄高鐵票");
        expData1.setNote("出差交通費");
        expData1.setAmountReqOc(new java.math.BigDecimal("1490.00"));
        expDataList.add(expData1);
        
        // ExpData 2: 餐費
        BasicReq.ExpData expData2 = new BasicReq.ExpData();
        expData2.setReimburseDetailId("DETAIL002");
        expData2.setExpCategory("MEAL");
        expData2.setExpDetail("出差期間餐費");
        expData2.setNote("3天出差餐費補助");
        expData2.setAmountReqOc(new java.math.BigDecimal("1800.00"));
        expDataList.add(expData2);
        
        // ExpData 3: 住宿費
        BasicReq.ExpData expData3 = new BasicReq.ExpData();
        expData3.setReimburseDetailId("DETAIL003");
        expData3.setExpCategory("HOTEL");
        expData3.setExpDetail("高雄商務旅館住宿費");
        expData3.setNote("2晚住宿費用");
        expData3.setAmountReqOc(new java.math.BigDecimal("3200.00"));
        expDataList.add(expData3);
        
        basicReq.setExpDataList(expDataList);
        
        System.out.println("Created BasicReq: " + basicReq);
        
        // 2. Call expense service to save basic data
        // System.out.println("\n2. Calling expense service to save basic data...");
        // String expenseUrl = serviceConfig.getExpenseServiceUrl() + "/expense/basic/save";
        // try {
        //     Object result = restTemplate.postForObject(expenseUrl, basicReq, Object.class);
        //     System.out.println("Expense service response: " + result);
        // } catch (Exception e) {
        //     System.out.println("Error calling expense service: " + e.getMessage());
        //     e.printStackTrace();
        // }
        
        // 3. Create another mock expense data for testing
        System.out.println("\n3. Creating second mock expense data...");
        BasicReq basicReq2 = new BasicReq();
        basicReq2.setFileNo("EXP2024002");
        basicReq2.setFillFinDep("行銷部");
        basicReq2.setFillId("USER002");
        basicReq2.setBudgetYear(2024);
        basicReq2.setApproveId("MANAGER002");
        
        // Create 3 more ExpData items
        java.util.List<BasicReq.ExpData> expDataList2 = new java.util.ArrayList<>();
        
        // ExpData 4: 辦公用品
        BasicReq.ExpData expData4 = new BasicReq.ExpData();
        expData4.setReimburseDetailId("DETAIL004");
        expData4.setExpCategory("OFFICE");
        expData4.setExpDetail("印表機耗材");
        expData4.setNote("辦公室印表機碳粉匣");
        expData4.setAmountReqOc(new java.math.BigDecimal("850.00"));
        expDataList2.add(expData4);
        
        // ExpData 5: 會議費用
        BasicReq.ExpData expData5 = new BasicReq.ExpData();
        expData5.setReimburseDetailId("DETAIL005");
        expData5.setExpCategory("MEETING");
        expData5.setExpDetail("客戶會議餐費");
        expData5.setNote("與客戶商談合作案餐費");
        expData5.setAmountReqOc(new java.math.BigDecimal("2500.00"));
        expDataList2.add(expData5);
        
        // ExpData 6: 廣告費用
        BasicReq.ExpData expData6 = new BasicReq.ExpData();
        expData6.setReimburseDetailId("DETAIL006");
        expData6.setExpCategory("ADVERTISING");
        expData6.setExpDetail("網路廣告投放");
        expData6.setNote("Google Ads 廣告費用");
        expData6.setAmountReqOc(new java.math.BigDecimal("5000.00"));
        expDataList2.add(expData6);
        
        basicReq2.setExpDataList(expDataList2);
        
        System.out.println("Created second BasicReq: " + basicReq2);
        
        // 4. Call expense service again
        System.out.println("\n4. Calling expense service for second request...");
        try {
            Object result2 = restTemplate.postForObject(expenseUrl, basicReq2, Object.class);
            System.out.println("Second expense service response: " + result2);
        } catch (Exception e) {
            System.out.println("Error calling expense service for second request: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("\n=== Expense Service Test Process Completed ===");
    }

    @Bean
    public CommandLineRunner run() {
        return args -> {
            // Run the original user client process
            // UserClientProcess();
            
            // Run the expense client process
            ExpenseClientProcess();
        };
    }
}