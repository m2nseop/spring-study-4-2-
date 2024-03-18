package jpabook_basic;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("hello");
        // 파라미터의 "hello"는 persistence.xml에 있는 <persistence-unit name="hello">의 "hello"와 같다.
        // 따라서 EntityManagerFactory가 persistence.xml에 있는 정보를 읽어오고 jpa(hibernate)를 사용할 수 있는 것이다. // + 데이터베이스와의 연동

        EntityManager em = emf.createEntityManager();
        // JPA는 작업을 해야할 경우( ex. 고객의 요청이 들어왔을 때 ) EntityManager를 통해서 작업을 해야한다.

        EntityTransaction tx = em.getTransaction();
        // JPA의 모든 작업은 Transaction 안에서 이루어져야한다.
        // 단순 조회 같은 경우는 Transaction 안에서 이루어지지 않아도 상관은 없다.

        tx.begin();

        try {

            tx.commit();
            // commit을 해야 변경사항이 db에 반영이 된다.
        } catch (Exception e) {
            // 문제가 생길경우 작업한 내용 롤백(초기화)
            tx.rollback();
        } finally {
            // 자원을 다 썼거나, 문제가 없을 경우 em을 close // em은 문제가 발생하더라도 반드시 close를 해주어야 한다. // 그래야 데이터베이스의 connection이 반환된다.
            em.close();
        }
        // 웹앱의 WAS를 사용한다고 했을 때 WAS가 내려가는 경우 EntityManagerFactory도 닫아줘야 resource pool들도 내려간다.
        emf.close();
    }
}