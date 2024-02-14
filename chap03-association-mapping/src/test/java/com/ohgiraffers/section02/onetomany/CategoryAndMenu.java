package com.ohgiraffers.section02.onetomany;

import jakarta.persistence.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "category_section01")
@Table(name = "tbl_category")
public class CategoryAndMenu {

    @Id
    @Column(name = "category_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryCode;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "ref_category_code")
    private Integer refCategoryCode;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "categoryCode")
    private List<Menu> menuList = new ArrayList<>();





    public CategoryAndMenu(){}

    public CategoryAndMenu(int categoryCode, String categoryName, Integer refCategoryCode) {
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.refCategoryCode = refCategoryCode;
    }

    @Override
    public String toString() {
        return "CategoryAndMenu{" +
                "categoryCode=" + categoryCode +
                ", categoryName='" + categoryName + '\'' +
                ", refCategoryCode=" + refCategoryCode +
                ", menuList=" + menuList +
                '}';
    }

    public List<Menu> getMenuList() {
        return menuList;
    }


//    public void setMenuList(List<Menu> menuList) {
//
//        List<Menu> newlist = new ArrayList<>();
//        for (Menu m : menuList){
//            m.setCategoryCode(this.categoryCode);
//            newlist.add(m);
//        }
//        this.menuList = menuList;
//    }

    public int getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getRefCategoryCode() {
        return refCategoryCode;
    }

    public void setRefCategoryCode(Integer refCategoryCode) {
        this.refCategoryCode = refCategoryCode;
    }
}
