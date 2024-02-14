package com.ohgiraffers.springjpa.order.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ohgiraffers.springjpa.menu.entity.Menu;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tbl_order")
public class MenuOrder {

    @Id
    @Column(name = "order_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderCode;

    @Column(name = "menu_code", nullable = false)
    private Integer menu;

    @Column(name = "order_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    // 중요한 부분
    @JsonIgnore
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "menuOrder")
    private List<Payments> payments = new ArrayList<>();



    public MenuOrder() {
    }

    public MenuOrder(int orderCode, Integer menu, Date orderDate, List<Payments> payments) {
        this.orderCode = orderCode;
        this.menu = menu;
        this.orderDate = orderDate;
        this.payments = payments;
    }

    public int getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(int orderCode) {
        this.orderCode = orderCode;
    }

    public Integer getMenu() {
        return menu;
    }

    public void setMenu(Integer menu) {
        this.menu = menu;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public List<Payments> getPayments() {
        return payments;
    }

    public void setPayments(List<Payments> payments) {
        this.payments = payments;
    }

    @Override
    public String toString() {
        return "MenuOrder{" +
                "orderCode=" + orderCode +
                ", menu=" + menu +
                ", orderDate=" + orderDate +
                ", payments=" + payments +
                '}';
    }
}
