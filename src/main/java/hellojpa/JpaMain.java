package hellojpa;

import jakarta.persistence.*;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        // 엔티티 매니저 팩토리는 웹서버가 올라오는 시점에 단 하나만 생성해서 애플리케이션 전체에 공유
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        
        // 엔티티 매니저는 쓰레드간에 공유 X (사용하고 버려야 한다.)
        EntityManager em = emf.createEntityManager();
        
        // JPA의 모든 데이터 변경은 트랜잭션 안에서 실행
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        //code
        try {
             // insert
//            Member member = new Member();
//            member.setId(2L);
//            member.setName("HelloB");
//            em.persist(member);
            // update
//            Member findMember = em.find(Member.class, 1L);
//            System.out.println("### findMember.id : " + findMember.getId());
//            System.out.println("### findMember.name : " + findMember.getName());
//            findMember.setName("HelloJPA");

            // JPQL (엔티티 객체를 대상으로 하는 쿼리)
            List<Member> result = em.createQuery("select m from Member m", Member.class)
                    .setFirstResult(5)
                    .setMaxResults(8)
                    .getResultList();
            for (Member member : result) {
                System.out.println("member.name: " + member.getName());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
