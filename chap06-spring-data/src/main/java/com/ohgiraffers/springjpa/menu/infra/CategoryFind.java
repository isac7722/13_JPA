package com.ohgiraffers.springjpa.menu.infra;

import com.ohgiraffers.springjpa.category.entity.Category;
import org.springframework.stereotype.Service;

@Service
public interface CategoryFind {

    Integer getCategory(int code);


}
