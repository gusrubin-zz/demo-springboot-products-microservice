package com.abinbev.ecommerce.products.domain.product;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.abinbev.ecommerce.products.domain.ServiceModel;

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

	@Override
	public Product findById(String id) {
		Optional<Product> productResult = productRepository.findById(id);
		if (productResult.isPresent()) {
			return productResult.get();
		}
		return null;
	}

	@Override
	public Product findByName(String name) {
		Optional<Product> productResult = productRepository.findByName(name);
		if (productResult.isPresent()) {
			return productResult.get();
		}
		return null;
	}

	@Override
	public String create(Product product) {
		if (!StringUtils.hasText(product.getName())) {
			throw new IllegalArgumentException("Product name is missing");
		}
		if (findByName(product.getName()) != null) {
			throw new IllegalStateException("Product name already registered");
		}

		// Here we can add many others business rules

		Product newProduct = productRepository.save(product);

		return newProduct.getId();
	}

	@Override
	public Product update(Product updatedProduct) {
		Product product = findById(updatedProduct.getId());
		if (product == null) {
			throw new IllegalStateException("Product not registered");
		}

		if (!updatedProduct.getName().equals(product.getName())) {
			product.setName(updatedProduct.getName());
		}

		// Here we can add other fields update rules

		return productRepository.save(product);
	}

	@Override
	public void delete(String id) {
		Product product = findById(id);
		if (product == null) {
			throw new IllegalStateException("Product not registered");
		}
		productRepository.delete(product);
	}

}
