package com.abinbev.ecommerce.products.web.product;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.abinbev.ecommerce.products.domain.product.Product;
import com.abinbev.ecommerce.products.domain.product.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	private final ProductService productService;

	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping()
	public ResponseEntity<List<Product>> getAllProducts(@RequestParam String productName) {

		List<Product> responseBody = new ArrayList<>();

		if (StringUtils.hasText(productName)) {
			responseBody.add(productService.findByName(productName));
		} else {
			responseBody.addAll(productService.findAll());
		}

		return ResponseEntity.ok(responseBody);
	}

	@GetMapping("/{productId}")
	public ResponseEntity<Product> getProductById(@PathVariable("productId") String productId) {

		return ResponseEntity.ok(productService.findById(productId));
	}

	@PostMapping()
	public ResponseEntity<Product> postProduct(@RequestBody Product requestBody, UriComponentsBuilder uriBuilder) {

		// We could do validations here before call the service

		String productId = productService.create(requestBody);

		return ResponseEntity.created(uriBuilder.path("/products/{productId}").buildAndExpand(productId).toUri())
				.build();
	}

	@PutMapping("/{productId}")
	public ResponseEntity<Product> postProduct(@RequestBody Product requestBody) {

		return ResponseEntity.ok(productService.update(requestBody));
	}

	@DeleteMapping("/{productId}")
	public ResponseEntity<Void> deleteProduct(@PathVariable("productId") String productId) {

		productService.delete(productId);

		return ResponseEntity.noContent().build();
	}

}
