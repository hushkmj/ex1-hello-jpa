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
            //팀 저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            //회원 저장
            Member member = new Member();
            member.setUsername("member1");
            em.persist(member);

            team.addMember(member);
//            em.flush();
//            em.clear();

            Team findTeam = em.find(Team.class, team.getId()); //1차 개시
            List<Member> members = findTeam.getMembers();

            System.out.println("========================");
            for (Member m : members) {
                System.out.println(m.getUsername());
            }
            System.out.println("========================");
//            Member findMember = em.find(Member.class, member.getId());
//            List<Member> members = findMember.getTeam().getMembers();
//
//            System.out.println("### members.size(): " + members.size());
//
//            for (Member m : members) {
//                System.out.println("### Member: " + m.getUsername());
//            }

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
