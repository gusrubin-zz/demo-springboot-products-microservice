package com.abinbev.ecommerce.products.domain.product;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;

public class Product {

	@Id
	String id;

	String name;

	String description;

	String brand;

	BigDecimal price;

}
