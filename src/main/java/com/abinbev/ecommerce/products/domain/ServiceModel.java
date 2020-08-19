package com.abinbev.ecommerce.products.domain;

import java.util.List;

public interface ServiceModel<T> {

	List<T> findAll();

	T findById(String id);

	T findByName(String name);

	String create(T t);

	T update(T t);

	void delete(String id);

}
