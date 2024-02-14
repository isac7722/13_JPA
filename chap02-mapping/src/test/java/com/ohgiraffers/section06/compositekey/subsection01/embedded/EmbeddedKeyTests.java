package com.ohgiraffers.section06.compositekey.subsection01.embedded;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

public class EmbeddedKeyTests {

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
    * jpa에서 복합 키를 메핑하는 방법에는 두가지가 있다
    * 첫번쨰 @EmbededId 어노테이션을 사용하는 방법이다
    * 이 방법은 복합 키를 구성하는 필드들을 하나의 클래스로 묶은 뒤, 해당 클래스를 @EmbeddedId 어노테이션을 사용ㅇ하여 메핑하는 것이다
    * 이 방법은 복합 키의 일부 필드만을 매핑할 수도 있기 때문에, 필드 수가 많은 경우에는 유연한 매핑이 가능하다는 장점이 있다
    * 두 번째는 @IdClass 어노테이션을 사용하는 방법이다
    * 이 방법은 복합 키를 구성하는 필드들을 별도의 클래스로 분리한 뒤, 해당 클래스를 @IdClass 어노테이션의 값으로 지정해주는 것이다
    * 이 방법은 복합 키를 구성하는 모든 필드를 한 번에 매핑할 수 있으며, 별도의 매핑 클래스를 사욯아지 않기 떄문에 코드가 간결하다는 장접이 있다
    *
    * 복합키의 매핑에서는 복합 키 클래스에 Equals와 hashCode 메소드를 구현해야 한다는 젘ㅁ에 주의해야한다
    * 이는 jpa에서 엔티티 객체의 동일성을 판단하기 위해 필요하다
    * 또한, @GenereatedValue 어노테이션을 사용하여 복합 키를 자동으로 생성하는 것응 ㄴ불가능하다는 점에 주의해야한다
    *
    * 두방식 모두 복합키 클래슨 ㄴ영속성 컨텍스트가 관리하지 ㅇ낳는다는 특징이 잇다, 큰 기능적 ㅇ차이도 존재하지 ㅏㄶ느다
    * */

    @Test
    public void 임베디드_아이디_사용한_복합키_테이블_매핑_테스트() {
        //given

        Member member = new Member();
        member.setMemberPk(new MemberPk(1, "user01"));
        member.setPhone("010-1234-5678");
        member.setAddress("서울시 종로구");

        //when
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(member);
        entityTransaction.commit();

        //then
        Member foundMember = entityManager.find(Member.class, member.getMemberPk());
        Assertions.assertEquals(member.getMemberPk(), foundMember.getMemberPk());
    }


}
