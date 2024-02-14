package com.ohgiraffers.springjpa.order.respository;

import com.ohgiraffers.springjpa.order.entity.Payments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentsRepository extends JpaRepository<Payments, Integer> {



}
