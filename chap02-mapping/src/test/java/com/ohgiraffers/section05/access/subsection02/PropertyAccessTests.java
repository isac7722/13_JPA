package com.ohgiraffers.section05.access.subsection02;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

public class PropertyAccessTests {

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

    @Test
    void 필드_접근_테스트(){

        //given
        Member member = new Member();
        member.setMemberNo(1);
        member.setMemberId("user01");
        member.setMemberPwd("pass01");
        member.setNickname("홍길동");

        //when
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(member);   // 1번
        entityTransaction.commit();      // 2번 (why?)

        //then
        String jpql = "SELECT a.memberId FROM member_section05_subsection02 a WHERE a.memberNo = 1";
        String registedMemberId = entityManager.createQuery(jpql, String.class).getSingleResult();
        System.out.println(registedMemberId);
        Assertions.assertEquals("0user01", registedMemberId);
    }

}
