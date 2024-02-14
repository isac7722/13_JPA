package com.ohgiraffers.springjpa.menu.service;

import com.ohgiraffers.springjpa.menu.dto.MenuDTO;
import com.ohgiraffers.springjpa.menu.entity.Menu;
import com.ohgiraffers.springjpa.menu.infra.CategoryFind;
import com.ohgiraffers.springjpa.menu.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class  MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private CategoryFind categoryFind; // 결합 구조를 느슨하게 하기 위해서 이렇게 DDD 나눔

    public Object insertMenu( MenuDTO menuDTO) {

        System.out.println(menuDTO);

        // 메뉴 이름이 존재하는가
        Menu menu = menuRepository.findByMenuName(menuDTO.getMenuName());

        System.out.println(menu);

        if(!Objects.isNull((menu))){
            return new String(menuDTO.getMenuName()+" menu exists");

        }

        // 가격정보를 확인

        if(menuDTO.getMenuPrice() < 0){
            return new String(menuDTO.getMenuPrice()+" price cannot be lower than 0");
        }

        // 카테고리 코드
        Integer categoryCode = categoryFind.getCategory(menuDTO.getCategoryCode());

        if (Objects.isNull(categoryCode)){
            return new String(menuDTO.getCategoryCode()+"는 존재하지 않습니다. ");
        }


        Menu newMenu = new Menu();
        newMenu.setMenuName(menuDTO.getMenuName());
        newMenu.setMenuPrice(menuDTO.getMenuPrice());
        newMenu.setCategory(menuDTO.getCategoryCode());

        Menu result = menuRepository.save(newMenu);

        return result;




    }

    public Integer findMenuCode(Integer menuCode) {

        Menu findMenu = menuRepository.findByMenuCode(menuCode);

        if (Objects.isNull(findMenu)){
            return null;
        }

        return findMenu.getMenuCode();


    }
}
