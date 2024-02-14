package com.ohgiraffers.springjpa.order.controller;


import com.ohgiraffers.springjpa.order.dto.OrderAndPaymentsVo;
import com.ohgiraffers.springjpa.order.entity.MenuOrder;
import com.ohgiraffers.springjpa.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Objects;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity order(@RequestBody HashMap<String , Integer> menuCode){

        MenuOrder result = orderService.order(menuCode.get("menuCode"));

        if (Objects.isNull(result)){
            return ResponseEntity.status(400).body("주문 실패");
        }

        OrderAndPaymentsVo orderAndPaymentsVo = new OrderAndPaymentsVo();
        orderAndPaymentsVo.setPayments(result.getPayments());
        orderAndPaymentsVo.setMenuCode(result.getMenu());
        orderAndPaymentsVo.setOrderDate(result.getOrderDate());

        return ResponseEntity.ok(orderAndPaymentsVo);

    }
}

