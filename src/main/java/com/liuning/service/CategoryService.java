package com.liuning.service;

import java.util.List;

import com.liuning.entity.Category;

public interface CategoryService {

	List<Category> getCategoryByChId(Integer cid);

}
