package com.ohgiraffers.springjpa.menu.infra;

import com.ohgiraffers.springjpa.category.entity.Category;
import com.ohgiraffers.springjpa.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryFindImpl implements CategoryFind{

    @Autowired
    CategoryService categoryService;

    @Override
    public Integer getCategory(int code) {
        return categoryService.findByCategory(code);
    }


}
