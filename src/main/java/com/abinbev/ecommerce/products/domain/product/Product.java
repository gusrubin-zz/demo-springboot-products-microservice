package com.abinbev.ecommerce.products.domain.product;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {

	@Id
	String id;

	String name;

	String description;

	String brand;

	BigDecimal price;

}
