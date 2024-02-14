package com.ohgiraffers.section07.subquery;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SubQueryTests {

    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @BeforeAll
    public static void initFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpatest");
    }

    @BeforeEach
    public void initManager() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterAll
    public static void closeFactory() {
        entityManagerFactory.close();
    }

    @AfterEach
    public void closeManager() {
        entityManager.close();
    }

    /* JPQL도 SQL 처럼 서브 쿼리를 지원한다.
     * 하지만 select, from절에서는 사용할 수 없고 where, having절에서만 사용이 가능하다. */

    @Test
    public void 서브쿼리를_이용한_메뉴_조회_테스트() {

        //given
        String categoryNameParameter = "한식";

        //when
        String jpql = "SELECT m FROM menu_section07 m WHERE m.categoryCode "
                + "= (SELECT c.categoryCode FROM category_section07 c WHERE c.categoryName = :categoryName)";
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class)
                .setParameter("categoryName", categoryNameParameter)
                .getResultList();

        //then
        assertNotNull(menuList);
        menuList.forEach(System.out::println);

    }


}