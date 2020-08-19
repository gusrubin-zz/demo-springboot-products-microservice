package com.abinbev.ecommerce.products.domain.product;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.abinbev.ecommerce.products.domain.ServiceModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductService implements ServiceModel<Product> {

	private final ProductRepository productRepository;

	@Autowired
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	public List<Product> findAll(@Nullable Boolean orderedByName) {
		if (orderedByName != null && Boolean.TRUE.equals(orderedByName)) {
			return productRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
		} else {
			return productRepository.findAll();
		}
	}

	@Override
	public Product findById(String id) {
		Optional<Product> productResult = productRepository.findById(id);
		if (productResult.isEmpty()) {
			log.error("Trying to find product with id not registered");
			throw new IllegalStateException("Product id not registered");
		}
		return productResult.get();
	}

	@Override
	public Product findByName(String name) {
		Optional<Product> productResult = productRepository.findByName(name);
		if (productResult.isEmpty()) {
			log.error("Trying to find product with name not registered");
			throw new IllegalStateException("Product name not registered");
		}
		return productResult.get();
	}

	@Override
	public String create(Product product) {
		if (!StringUtils.hasText(product.getName())) {
			throw new IllegalArgumentException("Product name is missing");
		}
		Optional<Product> productResult = productRepository.findByName(product.getName());
		if (productResult.isPresent()) {
			log.error("Trying to create product with name already registered");
			throw new IllegalStateException("Product name already registered");
		}

		// Here we can add many others business rules

		Product newProduct = productRepository.save(product);

		return newProduct.getId();
	}

	@Override
	public Product update(Product updatedProduct) {
		Optional<Product> productResult = productRepository.findById(updatedProduct.getId());
		if (productResult.isEmpty()) {
			log.error("Trying to update product with id not registered");
			throw new IllegalStateException("Product not registered");
		}
		Product product = productResult.get();

		if (!StringUtils.hasText(updatedProduct.getName())) {
			throw new IllegalArgumentException("Product name is missing");
		}

		if (!updatedProduct.getName().equals(product.getName())) {
			product.setName(updatedProduct.getName());
		}

		// Here we can add other fields update rules

		return productRepository.save(product);
	}

	@Override
	public void delete(String id) {
		Optional<Product> productResult = productRepository.findById(id);
		if (productResult.isEmpty()) {
			log.error("Trying to delete product not registered");
			throw new IllegalStateException("Product not registered");
		}

		productRepository.delete(productResult.get());
	}

}
