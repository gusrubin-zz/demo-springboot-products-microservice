package com.gustavorubin.abinbevecommerce.domain.product;

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
