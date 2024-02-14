package com.ohgiraffers.section04.paging;

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

public class PagingTests {

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

    /* 페이징 처리용 SQL은 DBMS에 따라 각각 문법이 다른 문제점을 안고 있다.
     * JPA는 이러한 페이징을 API를 통해 추상화해서 간단하게 처리할 수 있도록 제공해준다.
     * */

    @Test
    public void 페이징_API를_이용한_조회_테스트() {

        //given
        int offset = 10;      //조회를 건너 뛸 행 수
        int limit = 5;      //조회할 행 수

        //when
        String jpql = "SELECT m FROM menu_section04 m ORDER BY m.menuCode DESC";

        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class)
                .setFirstResult(offset)      //조회를 시작할 위치(0부터 시작)
                .setMaxResults(limit)      //조회할 데이터의 수
                .getResultList();

        //then
        assertNotNull(menuList);
        menuList.forEach(System.out::println);

    }



























}