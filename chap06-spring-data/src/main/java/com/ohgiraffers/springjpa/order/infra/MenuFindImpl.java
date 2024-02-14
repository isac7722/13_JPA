package com.ohgiraffers.springjpa.order.infra;

import com.ohgiraffers.springjpa.menu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class MenuFindImpl implements MenuFind{

    @Autowired
    private MenuService menuService;

    @Override
    public Integer findMenu(int menuCode) {



        Integer findCode = menuService.findMenuCode(menuCode);


        return findCode;
    }
}
