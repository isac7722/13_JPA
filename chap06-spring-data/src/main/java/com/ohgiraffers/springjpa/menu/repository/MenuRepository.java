package com.ohgiraffers.springjpa.menu.repository;

import com.ohgiraffers.springjpa.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  MenuRepository extends JpaRepository<Menu, Integer> {


    Menu findByMenuName(String name);

    Menu findByMenuCode(Integer menuCode);

}
