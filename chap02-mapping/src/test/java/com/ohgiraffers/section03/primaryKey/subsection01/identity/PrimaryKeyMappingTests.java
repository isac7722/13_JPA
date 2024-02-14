package com.ohgiraffers.section03.primaryKey.subsection01.identity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.Date;
import java.util.List;

public class PrimaryKeyMappingTests {

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
    * pk 에는 @Id 어노테이션과 @GeneratedValue 어노테이션을 사용한다
    * @Id 어노테이션은 엔티티 클래스에서 Primary key 역할을 하는 필드를 지정할 때 사용한다
    * @GeneratedValue 어노테이션을 함께 사용하면 pk 값을 자동으로 생성할 수 있다
    *
    * 데이터베이스마다 기본 키를 생성하는 방식이 서로 다르다
    *
    * strategy : 자동 생성 전략을 지정
    * GenerationType.IDENTITY: 기본 키 생성을 데이터베이스에 위임(MYSQL의 AUTO_INCREMENT)
    * GenerationType.SEQUENCE : 데이터베이스 시퀸스 객체 사용(Oracle의 sequence)
    * GenerationType.TABLE : 키 생성 테이블 사용
    * GenerationType.AUTO : 자동선택 (MYSQL 이라면 IDENTITY, ORACLE이라면 SEQUENCE로 선택)
    * Generator : strategy 값을 GenerationType.Table로 지정한 경우 사용되는 테이블 이름을 지정
    * initialValue : strategy 값을 Generation.Type SEQUENCE로 지정한 경우 시퀸스 초기값을 지정
    * allocationSize : strategy 값을 Generation.Type.SEQUENCE로 지정한 경우 시퀸스 증가치를 지정
    * */

    /* IDENTITY 전략 */
    @Test
    public void 식별자_매핑_테스트() {

        // given
        Member member = new Member();
        member.setMemberId("user01");
        member.setMemberPwd("pass01");
        member.setNickname("홍길동");
        member.setPhone("010-1234-5678");
        member.setAddress("서울시 종로구");
        member.setEnrollDate(new Date());
        member.setMemberRole("ROLE_MEMBER");
        member.setStatus("Y");

        Member member2 = new Member();
        member2.setMemberId("user02");
        member2.setMemberPwd("pass02");
        member2.setNickname("유관순");
        member2.setPhone("010-1234-5678");
        member2.setAddress("서울시 종로구");
        member2.setEnrollDate(new Date());
        member2.setMemberRole("ROLE_MEMBER");
        member2.setStatus("Y");

        Member member3 = new Member();
        member3.setMemberId("user03");
        member3.setMemberPwd("pass03");
        member3.setNickname("유관순1");
        member3.setPhone("010-1239-5678");
        member3.setAddress("서울시 종로구4421");
        member3.setEnrollDate(new Date());
        member3.setMemberRole("ROLE_MEMBER");
        member3.setStatus("Y");

        Member member4 = new Member();
        member4.setMemberId("user04");
        member4.setMemberPwd("pass04");
        member4.setNickname("유관순13");
        member4.setPhone("010-1434-5678");
        member4.setAddress("서울시 종로구2332");
        member4.setEnrollDate(new Date());
        member4.setMemberRole("ROLE_MEMBER");
        member4.setStatus("Y");


        Member member5 = new Member();
        member5.setMemberId("user05");
        member5.setMemberPwd("pass05");
        member5.setNickname("유관순5");
        member5.setPhone("010-1434-5678");
        member5.setAddress("서울시 종로구2332");
        member5.setEnrollDate(new Date());
        member5.setMemberRole("ROLE_MEMBER");
        member5.setStatus("Y");

        // when
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(member);
        entityManager.persist(member2);
        entityManager.persist(member3);
        entityManager.persist(member4);
        entityManager.persist(member5);
        entityTransaction.commit();

        //then
//        String jpql = "SELECT A.memberNo FROM member_section03_subsection01 A";
        String testQuery2 = "SELECT nickname FROM member_section03_subsection01";

//        List<Integer> memberNoList = entityManager.createQuery(jpql, Integer.class).getResultList();

        List<String> memberNoListTest = entityManager.createQuery(testQuery2, String.class).getResultList();

        memberNoListTest.forEach(System.out::println);

    }

}
