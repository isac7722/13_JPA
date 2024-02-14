package com.ohgiraffers.section05.groupfunction;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GroupFunctionTests {

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

    /* JPQL의 그룹함수는 COUNT, MAX, MIN, SUM, AVG로 SQL의 그룹함수와 별반 차이가 없다.
     * 단 몇가지 주의사항이 있다.
     * 1. 그룹함수의 반환 타입은 결과 값이 정수면 Long, 실수면 Double로 반환된다.
     * 2. 값이 없는 상태에서 count를 제외한 그룹 함수는 null이되고 count만 0이 된다.
     *    따라서 반환 값을 담기 위해 선언하는 변수 타입을 기본자료형으로 하게 되면, 조회 결과를 언박싱 할때 NPE가 발생한다.
     * 3. 그룹 함수의 반환 자료형은 Long or Double 형이기 때문에 Having 절에서 그룹 함수 결과값과 비교하기 위한 파라미터 타입은 Long or Double로 해야한다.
     * */

    @Test
    public void 특정_카테고리의_등록된_메뉴_수_조회() {

        //given
        int categoryCodeParameter = 4;

        //when
        String jpql = "SELECT COUNT(m.menuPrice) FROM menu_section05 m WHERE m.categoryCode = :categoryCode";
        long countOfMenu = entityManager.createQuery(jpql, Long.class)
                .setParameter("categoryCode", categoryCodeParameter)
                .getSingleResult();

        //then
        assertTrue(countOfMenu >= 0);
        System.out.println(countOfMenu);

    }

    @Test
    public void count를_제외한_다른_그룹함수의_조회결과가_없는_경우_테스트() {

        //given
        int categoryCodeParameter = 2;

        //when
        String jpql = "SELECT SUM(m.menuPrice) FROM menu_section05 m WHERE m.categoryCode = :categoryCode";

        //then
        assertThrows(NullPointerException.class, () -> {
            /* 반환 값을 담을 변수의 타입을 기본 자료형으로 하는 경우 Wrapper 타입을 언박싱 하는 과정에서 NPE이 발생하게 된다. */
            long sumOfPrice = entityManager.createQuery(jpql, Long.class)
                    .setParameter("categoryCode", categoryCodeParameter)
                    .getSingleResult();

        });

        assertDoesNotThrow(() -> {
            /* 반환 값을 담는 변수를 Wrapper 타입으로 선언해야 null 값이 반환 되어도 NPE가 발생하지 않는다. */
            Long sumOfPrice = entityManager.createQuery(jpql, Long.class)
                    .setParameter("categoryCode", categoryCodeParameter)
                    .getSingleResult();

        });


    }

    @Test
    public void groupby절과_having절을_사용한_조회_테스트() {

        //given
        long minPrice = 50000L;  //그룹함수의 반환 타입은 Long이므로 비교를 위한 파라미터도 Long 타입을 사용해야 한다.

        //when
        String jpql = "SELECT m.categoryCode, SUM(m.menuPrice)"
                + " FROM menu_section05 m"
                + " GROUP BY m.categoryCode"
                + " HAVING SUM(m.menuPrice) >= :minPrice";

        List<Object[]> sumPriceOfCategoryList = entityManager.createQuery(jpql, Object[].class)
                .setParameter("minPrice", minPrice)
                .getResultList();

        //then
        assertNotNull(sumPriceOfCategoryList);
        sumPriceOfCategoryList.forEach(row -> {
            Arrays.stream(row).forEach(System.out::println);
        });
    }





















}