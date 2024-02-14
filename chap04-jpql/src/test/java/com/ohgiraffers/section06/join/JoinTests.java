package com.ohgiraffers.section06.join;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.stream.Stream;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JoinTests {

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

    /* 조인의 종류
     * 1. 일반 조인 : 일반적인 SQL 조인을 의미 (내부 조인, 외부 조인, 컬렉션 조인, 세타 조인)
     * 2. 페치 조인 : JPQL에서 성능 최적화를 위해 제공하는 기능으로 연관 된 엔티티나 컬렉션을 한 번에 조회할 수 있다.
     *              지연 로딩이 아닌 즉시 로딩을 수행하며 join fetch 명령어를 사용한다.
     * */

    @Test
    public void 내부조인을_이용한_조회_테스트 () {

        /* Menu 엔티티에 대한 조회만 일어나고 Category 엔티티에 대한 조회는 나중에 필요할때 일어난다.
         * select의 대상은 영속화하여 가져오지만 조인의 대상은 영속화하여 가져오지 않는다. */

        //when
        String jpql = "SELECT m FROM menu_section06 m JOIN m.category c";
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).getResultList();

        //then
        assertNotNull(menuList);
        menuList.forEach(System.out::println);

    }

    @Test
    public void 외부조인을_이용한_조회_테스트() {

        //when
        String jpql = "SELECT m.menuName, c.categoryName FROM menu_section06 m RIGHT JOIN m.category c "
                + "ORDER BY m.category.categoryCode";
        List<Object[]> menuList = entityManager.createQuery(jpql, Object[].class).getResultList();

        //then
        assertNotNull(menuList);
        menuList.forEach(row -> {
            Stream.of(row).forEach(col -> System.out.print(col + " "));
            System.out.println();
        });
    }

    @Test
    public void 컬렉션조인을_이용한_조회_테스트() {

        /* 컬렉션 조인은 의미상 분류 된 것으로 컬렉션을 지니고 있는 엔티티를 기준으로 조인하는 것을 말한다. */

        //when
        String jpql = "SELECT c.categoryName, m.menuName FROM category_section06 c LEFT JOIN c.menuList m";
        List<Object[]> categoryList = entityManager.createQuery(jpql, Object[].class).getResultList();

        //then
        assertNotNull(categoryList);
        categoryList.forEach(row -> {
            Stream.of(row).forEach(col -> System.out.print(col + " "));
            System.out.println();
        });

    }

    @Test
    public void 세타조인을_이용한_조회_테스트() {

        /* 세타 조인은 조인 되는 모든 경우의 수를 다 반환하는 크로스 조인과 같다. */

        //when
        String jpql = "SELECT c.categoryName, m.menuName FROM category_section06 c, menu_section06 m";
        List<Object[]> categoryList = entityManager.createQuery(jpql, Object[].class).getResultList();

        //then
        assertNotNull(categoryList);
        categoryList.forEach(row -> {
            Stream.of(row).forEach(col -> System.out.print(col + " "));
            System.out.println();
        });

    }

    @Test
    public void 페치조인을_이용한_조회_테스트 () {

        /* 페치 조인을 하면 처음 SQL 실행 후 로딩할 때 조인 결과를 다 조회한 뒤에 사용하는 방식이기 때문에 쿼리 실행 횟수가 줄어들게 된다.
         * 대부분의 경우 성능이 향상 된다. */

        //when
        String jpql = "SELECT m FROM menu_section06 m JOIN FETCH m.category c";
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).getResultList();

        //then
        assertNotNull(menuList);
        menuList.forEach(System.out::println);

    }












}