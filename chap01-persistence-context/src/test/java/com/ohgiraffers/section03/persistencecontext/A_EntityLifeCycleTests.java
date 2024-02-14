package com.ohgiraffers.section03.persistencecontext;



import jakarta.persistence.*;
import org.junit.jupiter.api.*;

import java.awt.geom.GeneralPath;

public class A_EntityLifeCycleTests {
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
    static void closeFactory() {
        entityManagerFactory.close();
    }

    @AfterEach
    void closeManager() {
        entityManager.close();
    }

    /*
     * 영속성 컨텍스트는 엔티티 매니저가 엔티티 객체를 저장하는 공간으로 엔티티 객체를 보관하고 관리한다
     * 엔티티 매니저가 생성 괼 때 하나의 영속성 컨텍스트가 만들어 진다
     *
     * 엔티티의 생명주기
     * 비영속, 영속, 준영속, 삭제 상태
     * */

    @Test
    public void 비영속_테스트() {

        // given
        Menu foundMenu = entityManager.find(Menu.class, 11);

        // 객체만 생성하면 영속성 컨텍스트나 데이터베이스와 관련 없는 비영속 상태이다.
        Menu newMenu = new Menu();
        newMenu.setMenuCode(foundMenu.getMenuCode());
        newMenu.setMenuName(foundMenu.getMenuName());
        newMenu.setMenuPrice(foundMenu.getMenuPrice());
        newMenu.setCategoryCode(foundMenu.getCategoryCode());
        newMenu.setOrderableStatus(foundMenu.getOrderableStatus());

        Menu copyMenu = foundMenu;

        //when
        boolean isTrue = (foundMenu == newMenu);
//        boolean result = copyMenu == foundMenu;

        // then
        Assertions.assertFalse(isTrue);

//        Assertions.assertTrue(result);
    }

    @Test
    void 영속성_연속_조회_테스트() {
        /*
         * 엔티티 매니저가 영속성 컨텍스트에 엔티티 객체를 저장(persist)하면 영속성 컨텍스트가 엔티티 객체를 관ㄹ하기 되고
         * 이를 영속 상태라고 한다. Find(), jpql을 사용한 조회도 영속 상태가 된다
         * */

        // given
        Menu foundMenu1 = entityManager.find(Menu.class, 11);
        Menu foundMenu2 = entityManager.find(Menu.class, 11);

        // when
        boolean isTrue = (foundMenu1 == foundMenu2);

        // then
        Assertions.assertTrue(isTrue);
    }

    @Test
    void 영속성_객체_추가_테스트() {

        // Menu Entity 의 @GeneratedValue( startegy=GenerationType.IDENTITY) 설정을 잠시 수석하고 테스트 수행

        // Given
        Menu menuToRegist = new Menu();
        menuToRegist.setMenuCode(500);
        menuToRegist.setMenuName("수박죽");
        menuToRegist.setMenuPrice(12999);
        menuToRegist.setCategoryCode(1);
        menuToRegist.setOrderableStatus("Y");

        // when
        entityManager.persist(menuToRegist);
        Menu foundMenu = entityManager.find(Menu.class, 500);
        boolean isTrue = (menuToRegist == foundMenu);

        // persistence context에 값이 있기 때문에 굳이 db로 안간다
        // persistence context에 값이 없으면 db에서 찾아본다

        Assertions.assertTrue(isTrue);
    }

    @Test
    void 준영속성_detach_테스트() {
        // given
        Menu foundMenu = entityManager.find(Menu.class, 11);
        Menu foundMenu1 = entityManager.find(Menu.class, 12);

        /*
         * 영속성 컨텍스트가 관리하던 엔티티 객체를 관리하지 않는 상태가 된다면 준여속 상태가 된다
         * 그 중 detach는 특정 엔티티만 준영속 상태로 만든다
         * */
        // when
        entityManager.detach(foundMenu1);

        foundMenu.setMenuPrice(5000);
        foundMenu1.setMenuPrice(5000);

        Assertions.assertEquals(5000, entityManager.find(Menu.class, 11).getMenuPrice());

        // 준영속을 다시 영속하려면 merge
        entityManager.merge(foundMenu1);


        // 비영속화 시켜서 다시 불러온다
        Assertions.assertEquals(5000, entityManager.find(Menu.class, 12).getMenuPrice());


        // remove -> 비영속화



    }

    @Test
    void 준영속성_clear_테스트(){

        // given
        Menu foundMenu1 = entityManager.find(Menu.class, 11);
        Menu foundMenu2 = entityManager.find(Menu.class, 12);

        // when
        entityManager.clear();

        foundMenu1.setMenuPrice(1000);
        foundMenu2.setMenuPrice(1000);

        Assertions.assertNotEquals(1000, entityManager.find(Menu.class,11).getMenuPrice());
        Assertions.assertNotEquals(1000, entityManager.find(Menu.class,12).getMenuPrice());
    }

    @Test
    void close_테스트(){

        // given
        Menu foundMenu1 = entityManager.find(Menu.class, 11);
        Menu foundMenu2 = entityManager.find(Menu.class, 12);

        // when
        entityManager.close();

        foundMenu2.setMenuPrice(5000);
        foundMenu1.setMenuPrice(5000);

        // then
        // 영속성 context
        Assertions.assertEquals(5000,entityManager.find(Menu.class, 11).getMenuPrice());
        Assertions.assertEquals(5000,entityManager.find(Menu.class, 12).getMenuPrice());
    }

    @Test
    public void 삭제_remove_테스트(){
        /*
        * remove: 엔티티를 영속성 컨텍스트 및 데이터베이스에서 삭제한다
        * 단, 트랜젝션을 제어하지 않으면 영구 반영되지는 않는다
        * 트랜잭션을 커밋하는 순간 영속성 컨택스트에서 관리하는 엔티티 객체가 데이터베이스에 반영되게 한다(이를 flush)
        * flush: 영속성 컨텍스트의 변경 내용을 데이터베이스에 동기화 하는 작업
        * */

        // given
        Menu foundMenu = entityManager.find(Menu.class, 2);

        EntityTransaction entityTransaction = entityManager.getTransaction();


        // when

        entityTransaction.begin();

        entityManager.remove(foundMenu);

        entityTransaction.commit();

        Menu refoundMenu = entityManager.find(Menu.class, 2);

        Assertions.assertEquals(2, foundMenu.getMenuCode());
        Assertions.assertEquals(null, refoundMenu);
    }

    /*
    * 병합(merge): 파라미터로 넘어온 준영속 객체의 식별 값으로 1차 캐시에서 엔티티 객체를 조회한다
    * 만약 1차 캐시에 엔티티가 없으면 데이터베이스에서 엔티티를 조회하고 1차 캐시에 저장한다
    * 조회한 영속 엔티티 객체에 준영속 상태의
    *
    * */
    @Test
    void 병합_merge_수정_테스트(){

        // given
        Menu menuToDetach = entityManager.find(Menu.class,3);
        entityManager.detach(menuToDetach);

        // when
        menuToDetach.setMenuName("수박죽");
        Menu refoundMenu = entityManager.find(Menu.class, 3);

        // 준영속 엔티티와 영속 엔티티의 해쉬코드는 다른 상태다
        System.out.println(menuToDetach.hashCode());
        System.out.println(refoundMenu.hashCode());

        entityManager.merge(menuToDetach);



        // then
        Menu mergedMenu = entityManager.find(Menu.class, 3);
        Assertions.assertEquals("수박죽",mergedMenu.getMenuName());
    }

    @Test
    void 병합_merge_삽입_테스트(){

        // given
        Menu menuToDetach = entityManager.find(Menu.class, 3); // 1
        entityManager.detach(menuToDetach);

        // when
        menuToDetach.setMenuCode(999);
        menuToDetach.setMenuName("수박죽");


        // id를 바꿔서 다른 객체로 익식함 / id를 기준으로 persistence context를 최신화
        entityManager.merge(menuToDetach); //2 - merge가 안됨
        // then
        Menu mergedMenu = entityManager.find(Menu.class, 999); // 3
        Assertions.assertEquals("수박죽",mergedMenu.getMenuName());
    }


    @Test
    void 병합_merge_삽입_테스트2(){

        // given
        Menu menuToDetach = entityManager.find(Menu.class, 3); // 1
        entityManager.detach(menuToDetach);

        // when
        menuToDetach.setMenuCode(125);
        menuToDetach.setMenuName("수박죽");


        // id를 바꿔서 다른 객체로 익식함 / id를 기준으로 persistence context를 최신화
        entityManager.merge(menuToDetach); //2 - merge가 안됨
        // then
        Menu mergedMenu = entityManager.find(Menu.class, 125); // 3
        Assertions.assertEquals("수박죽",mergedMenu.getMenuName());
    }

    @Test
    void 병합_merge_삽입_테스트3(){

        // given
        Menu menuToDetach = entityManager.find(Menu.class, 3); // 1
        entityManager.detach(menuToDetach);

        // when
        menuToDetach.setMenuCode(999);
        menuToDetach.setMenuName("수박죽");

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(menuToDetach);
        transaction.commit();


        // id를 바꿔서 다른 객체로 익식함 / id를 기준으로 persistence context를 최신화
        entityManager.merge(menuToDetach); //2 - merge가 안됨
        // then
        Menu mergedMenu = entityManager.find(Menu.class, 999); // 3
        Assertions.assertEquals("수박죽",mergedMenu.getMenuName());
    }


}
