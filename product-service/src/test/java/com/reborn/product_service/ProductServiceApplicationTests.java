package com.reborn.product_service;

import com.reborn.product_service.model.Product;
import com.reborn.product_service.repository.ProductRepository;
import com.reborn.product_service.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductServiceApplicationTests {

	@Autowired
	private ProductService productService;

	@MockBean
	private ProductRepository productRepository;

	private Product testProduct;

	@BeforeEach
	void setUp() {
		testProduct = new Product();
		testProduct.setId(1L);
		testProduct.setName("Test Product");
		testProduct.setDescription("Test Description");
		testProduct.setPrice(99.99);
		testProduct.setAccountId(1L);
	}

	@Test
	void contextLoads() {
		assertNotNull(productService);
	}

	@Test
	void addProduct_Success() {
		when(productRepository.save(any(Product.class))).thenReturn(testProduct);

		Product savedProduct = productService.addProduct(testProduct);

		assertNotNull(savedProduct);
		assertEquals(testProduct.getName(), savedProduct.getName());
		assertEquals(testProduct.getDescription(), savedProduct.getDescription());
		assertEquals(testProduct.getPrice(), savedProduct.getPrice());
		verify(productRepository, times(1)).save(any(Product.class));
	}

	@Test
	void getProducts_Success() {
		List<Product> productList = Arrays.asList(testProduct);
		when(productRepository.findAll()).thenReturn(productList);

		List<Product> products = productService.getProducts();

		assertNotNull(products);
		assertEquals(1, products.size());
		assertEquals(testProduct.getName(), products.get(0).getName());
		verify(productRepository, times(1)).findAll();
	}

	@Test
	void getProduct_Success() {
		when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));

		Optional<Product> foundProduct = productService.getProduct(1L);

		assertTrue(foundProduct.isPresent());
		assertEquals(testProduct.getName(), foundProduct.get().getName());
		verify(productRepository, times(1)).findById(1L);
	}

	@Test
	void getProduct_NotFound() {
		when(productRepository.findById(999L)).thenReturn(Optional.empty());

		Optional<Product> foundProduct = productService.getProduct(999L);

		assertTrue(foundProduct.isEmpty());
		verify(productRepository, times(1)).findById(999L);
	}

	@Test
	void updateProduct_Success() {
		Product updatedProduct = new Product();
		updatedProduct.setName("Updated Product");
		updatedProduct.setDescription("Updated Description");
		updatedProduct.setPrice(149.99);
		updatedProduct.setAccountId(1L);

		when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
		when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

		Product result = productService.updateProduct(1L, updatedProduct);

		assertNotNull(result);
		assertEquals(updatedProduct.getName(), result.getName());
		assertEquals(updatedProduct.getDescription(), result.getDescription());
		assertEquals(updatedProduct.getPrice(), result.getPrice());
		verify(productRepository, times(1)).findById(1L);
		verify(productRepository, times(1)).save(any(Product.class));
	}

	@Test
	void updateProduct_NotFound() {
		Product updatedProduct = new Product();
		when(productRepository.findById(999L)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> {
			productService.updateProduct(999L, updatedProduct);
		});

		verify(productRepository, times(1)).findById(999L);
		verify(productRepository, never()).save(any(Product.class));
	}

	@Test
	void deleteProduct_Success() {
		when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
		doNothing().when(productRepository).delete(any(Product.class));

		assertDoesNotThrow(() -> productService.deleteProduct(1L));

		verify(productRepository, times(1)).findById(1L);
		verify(productRepository, times(1)).delete(any(Product.class));
	}

	@Test
	void deleteProduct_NotFound() {
		when(productRepository.findById(999L)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> {
			productService.deleteProduct(999L);
		});

		verify(productRepository, times(1)).findById(999L);
		verify(productRepository, never()).delete(any(Product.class));
	}
}
