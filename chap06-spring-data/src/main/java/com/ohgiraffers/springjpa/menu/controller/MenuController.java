package com.ohgiraffers.springjpa.menu.controller;


import com.ohgiraffers.springjpa.menu.dto.MenuDTO;
import com.ohgiraffers.springjpa.menu.entity.Menu;
import com.ohgiraffers.springjpa.menu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


// RestController는 ViewResolver 타지 않음
@RestController
@RequestMapping("/menus")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @PostMapping
    public ResponseEntity<Object> insertMenu(@RequestBody MenuDTO menu){

        Object result = menuService.insertMenu(menu);

        if (!(result instanceof Menu)){
            return ResponseEntity.status(404).body("등록 실패");
        }
        Menu response = (Menu) result;



        return ResponseEntity.ok(response);
    }
}
