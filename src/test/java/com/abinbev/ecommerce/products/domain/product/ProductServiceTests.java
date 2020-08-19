package com.abinbev.ecommerce.products.domain.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class ProductServiceTests {

	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	private ProductService productService;

	/*
	 * Utility Methods
	 */

	Product generateProduct(String id) {
		return Product.builder().id(id).name("Beer").description("This beer is the beer").brand("Brand")
				.price(BigDecimal.TEN).build();
	}

	Product generateProduct(String id, String name) {
		return Product.builder().id(id).name(name).description("This beer is the beer").brand("Brand")
				.price(BigDecimal.TEN).build();
	}

	List<Product> generateProductList() {
		List<Product> products = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			products.add(Product.builder().id(String.valueOf(i)).name("Beer " + i).description("This beer is " + i)
					.brand("Brand " + i).price(BigDecimal.valueOf(i)).build());
		}
		return products;
	}

	/*
	 * findAll method tests
	 */

	@Test
	void findAllMustReturnProductList() {
		List<Product> originalProductList = generateProductList();
		Mockito.when(productRepository.findAll()).thenReturn(originalProductList);

		List<Product> returnedProductList = productService.findAll();

		Assertions.assertEquals(originalProductList, returnedProductList);
	}

	/*
	 * findById method tests
	 */

	@Test
	void findByIdMustReturnProductWithValidId() {
		Optional<Product> productResult = Optional.of(generateProduct("1"));
		Mockito.when(productRepository.findById("1")).thenReturn(productResult);

		Product returnedProduct = productService.findById("1");

		Assertions.assertEquals(productResult.get(), returnedProduct);
	}

	@Test
	void findByIdWithInvalidIdMustThrowException() {
		Optional<Product> productResult = Optional.of(generateProduct("1", "Skol"));
		Mockito.when(productRepository.findById("1")).thenReturn(productResult);

		Assertions.assertThrows(IllegalStateException.class, () -> productService.findById("2"));
	}

	/*
	 * findByName method tests
	 */

	@Test
	void findByNameMustReturnProductWithValidName() {
		Optional<Product> productResult = Optional.of(generateProduct("1", "Skol"));
		Mockito.when(productRepository.findByName("Skol")).thenReturn(productResult);

		Product returnedProduct = productService.findByName("Skol");

		Assertions.assertEquals(productResult.get(), returnedProduct);
	}

	@Test
	void findByNameWithInvalidNameMustThrowException() {
		Optional<Product> productResult = Optional.of(generateProduct("1", "Skol"));
		Mockito.when(productRepository.findByName("Skol")).thenReturn(productResult);

		Assertions.assertThrows(IllegalStateException.class, () -> productService.findByName("Bohemia"));
	}

	/*
	 * create method tests
	 */

	@Test
	void createProductMustCompleteWithSuccess() {
		Product product = generateProduct("1");
		Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);

		String productId = productService.create(product);

		Assertions.assertEquals(product.getId(), productId);
	}

	@Test
	void createProductWithNoNameMustThrowException() {
		Product product = generateProduct("1", null);

		Assertions.assertThrows(IllegalArgumentException.class, () -> productService.create(product));
	}

	@Test
	void createProductWithNameAlreadyRegisteredMustThrowException() {
		Optional<Product> productResult = Optional.of(generateProduct("1", "Skol"));
		Mockito.when(productRepository.findByName("Skol")).thenReturn(productResult);

		Product newProduct = generateProduct("2", "Skol");

		Assertions.assertThrows(IllegalStateException.class, () -> productService.create(newProduct));
	}

	/*
	 * update method tests
	 */

	@Test
	void updateProductNameMustCompleteWithSuccess() {
		Product currentProduct = generateProduct("1", "Skol");
		Optional<Product> productResult = Optional.of(currentProduct);
		Mockito.when(productRepository.findById("1")).thenReturn(productResult);

		Product newProduct = generateProduct("1", "Bohemia");
		Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(newProduct);

		Product updatedProduct = productService.update(newProduct);

		Assertions.assertEquals(newProduct.getName(), updatedProduct.getName());
	}

	@Test
	void updateProductNotRegisteredMustThrowException() {
		Mockito.when(productRepository.findById("1")).thenReturn(Optional.empty());

		Product newProduct = generateProduct("1", "Bohemia");

		Assertions.assertThrows(IllegalStateException.class, () -> productService.update(newProduct));
	}

	@Test
	void updateProductWithNoNameMustThrowException() {
		Product currentProduct = generateProduct("1", "Skol");
		Optional<Product> productResult = Optional.of(currentProduct);
		Mockito.when(productRepository.findById("1")).thenReturn(productResult);

		Product newProduct = generateProduct("1", null);

		Assertions.assertThrows(IllegalArgumentException.class, () -> productService.update(newProduct));
	}

	/*
	 * delete method tests
	 */

	@Test
	void deleteProductMustCompleteWithSuccess() {
		Product currentProduct = generateProduct("1", "Skol");
		Optional<Product> productResult = Optional.of(currentProduct);
		Mockito.when(productRepository.findById("1")).thenReturn(productResult);

		Assertions.assertDoesNotThrow(() -> productService.delete("1"));
	}

	@Test
	void deleteProductNotRegisteredMustThrowException() {
		Mockito.when(productRepository.findById("1")).thenReturn(Optional.empty());

		Assertions.assertThrows(IllegalStateException.class, () -> productService.delete("1"));
	}

}
