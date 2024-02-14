package com.ohgiraffers.springjpa.category.controller;

import com.ohgiraffers.springjpa.category.entity.Category;
import com.ohgiraffers.springjpa.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity insertCategory(@RequestBody HashMap<String , String> categoryName){

        // 카테고리 유효성
        if (Objects.isNull(categoryName)){
            return ResponseEntity.status(404).body("이름은 필수여~~~");
        }

        Category result = categoryService.insertCategory(categoryName.get("categoryName"));

        if (Objects.isNull(result)){
            return ResponseEntity.status(500).body("서버에서 오류가 발생되었습니다.");
        }

        return ResponseEntity.ok(result);



    }
}
