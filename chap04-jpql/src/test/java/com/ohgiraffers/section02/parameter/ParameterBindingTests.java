package com.ohgiraffers.section02.parameter;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.List;

public class ParameterBindingTests {

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



    /*
    * 파라미티 바인딩 방법
    * 1. 이름 기준 파라믿터(named parameters)
    * ':' 다음에 이름 기준 파라미터를 지정한다
    * 2. 위치 기준 파라미터(positional Parameters)
    * '?' 다음에 값을 주고 위치 값을 1부터 시작한다
    *
    * */

    @Test
    void 이름_기준_파라미터_바인딩(){

        //given
        String menuNameParameter = "한우딸기국밥";

        //when
        String jpql = "SELECT m FROM menu_section02 m WHERE m.menuName = :menuName";

        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).setParameter("menuName", menuNameParameter).getResultList();

        Assertions.assertNotNull(menuList);
        menuList.forEach(System.out::println);
    }

    @Test
    void 위치_기준_파라미터_바인딩_메뉴_목록_조회_테스트(){
        //given
        String menuNameParameter = "한우딸기국밥";

        //when
        String jpql = "SELECT m FROM menu_section02 m WHERE m.menuName = ?1";

        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class)
                .setParameter(1, menuNameParameter)
                .getResultList();

        Assertions.assertNotNull(menuList);
        menuList.forEach(System.out::println);
    }

}
