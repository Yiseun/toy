package step03;

import static org.junit.Assert.fail;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.Test;

import dev.sample.model.Major;
import dev.sample.model.Student;

public class AppTest {

	@Test
	public void test() {
		fail("Not yet implemented");
	}

	@Test
	public void testSave() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("step03");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		/*
		 * 1. 학과 데이터 생성, 저장
		 * 
		 * 2. 학생 데이터 생성(학생1 Yoo, 학생2 Kang) 및 컴퓨터 공학과와 연관관계를 설정 후 저장
		 */
		
		tx.begin();
		
		// 1.
		Major major1 = new Major("컴퓨터 공학");
		em.persist(major1);
		
		// 2-1. 학생 1 생성, 저장
		Student student1 = new Student("Yoo");
		student1.setMajor(major1); // 연관관계 설정
		em.persist(student1);
		
		// 2-2. 학생2 생성, 저장
		Student student2 = new Student("Kang");
		student2.setMajor(major1); // 연관관계 설정
		em.persist(student2); //커밋되기전에만 하면된다 굳이 여기일필요없음
		
		tx.commit();
		// SELECT * FROM student; major_id 값 적용된 것 확인
	}
	
	@Test
	public void readStudents() {// JPA로 학생 리스트 조회
		//직접꺼내올수있는데 캐시에 남아있는 데이터처럼 미리 꺼내놓는거 그래서 두줄만 가져옴
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("step03");
		EntityManager em = emf.createEntityManager();
		
		Major major = em.find(Major.class, 1L); //전공 엔티티를 통해 학생 리스트를 가져왔다
//		System.out.println(major);
//		System.out.println(major.getStudents());
		
		List<Student> students = major.getStudents();
		for (Student student : students) {
			System.out.println(student.getStudentName());
		}
	}
	
	@Test
	public void testSaveNonOwnerWithJPA() { // JPA를 적용한 양방향 연관관계에서 연관관계의 주인이 아닌 곳에만 값을 입력할 경우
		//student테이블을 조회했을때
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("step03");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		// 학과 데이터 저장
		Major major1 = new Major("컴퓨터 공학");
		em.persist(major1);
		
		// 학생1 저장
		Student student1 = new Student("Yoo");
//		student1.setMajor(major1); // 이게 원래 연관관계의 주인인 곳에 연관관계 설정
		
		//학생엔티티로 연관관계설정하면 끝인데 반대로 설정해보자는거
		//만약 연관관계의 주인이 아닌 곳에만 연관관계를 설정할 경우,
		List<Student> students = major1.getStudents(); //학생들 리스트가 나오는데 지금은 값이 비어있다
		//이때 students에는 값이 없다 비어있는 ArrayList만 있다
		students.add(student1); //학생이 고른게 아니라 학과에서 강제로 학생을 편입시킨거?
		em.persist(student1);
		
		// 학생2 저장
		Student student2 = new Student("Kang");
		students.add(student2);
		em.persist(student2);
		
		//외래키가 있는쪽이 연관관계의 주인이랬는데
		//연관관계의 주인이 있는쪽에서 해줘야한다 뭘? 제대로 못들음
		
		tx.commit();
		
	}
	
	@Test
	public void testSaveOwnerWithOOP() { // OOP 순수한 객체지향 코드에서 연관관계의 설정
		//객체지향일때는 양쪽 다 해야한다
		//학생한테도 등록시켜야하고 학과에도 등롟켜야하고
		Major major1 = new Major("컴퓨터 공학");
		
		Student student1 = new Student("Yoo");
		student1.setMajor(major1); // 연관관계의 주인 방향에서 연관관계 설정
		
		Student student2 = new Student("Kang");
		student2.setMajor(major1); // 연관관계의 주인 방향에서 연관관계 설정
		
		List<Student> students = major1.getStudents();
		System.out.println("students.size : " + students.size());
		
	}
	
	@Test
	public void biWithPureOOP() { // OOP 순수한 객체지향 코드에서 양방향 연관관계 적용
		//객체지향일때는 양쪽 다 해야한다
		//학생한테도 등록시켜야하고 학과에도 등롟켜야하고
		Major major1 = new Major("컴퓨터 공학");
		List<Student> students = major1.getStudents();
		
		Student student1 = new Student("Yoo");
		student1.setMajor(major1); // 연관관계의 주인 방향에서 연관관계 설정
		students.add(student1); // 반대편에서도 연관관계 설정
		
		Student student2 = new Student("Kang");
		student2.setMajor(major1); // 연관관계의 주인 방향에서 연관관계 설정
		students.add(student2); // 반대편에서도 연관관계 설정
		
		System.out.println("student.size:" + students.size());
	}
	
	@Test
	public void biWithJPA() { // JPA를 활용한 양방향 맵핑
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("step03");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		Major major1 = new Major("국문학");
		em.persist(major1);
		
		Student student1 = new Student("Park");
		
		// 양방향 연관관계 설정
		student1.setMajor(major1); // 연관관계의 주인 방향에서 설정
		List<Student> students = major1.getStudents();
		students.add(student1); // 반대편에서도 설정		
		em.persist(student1); // 영속화
		
		//학생2도 생성 및 연관관계 설정, 영속화
		Student student2 = new Student("Kim");
		student2.setMajor(major1);
		students.add(student2);
		em.persist(student2);
		
		
		tx.commit();
		
		//조인 쿼리 써보는거
		//SELECT s.*, m.major_name FROM student s JOIN major m ON
	}
	

//	@Test
//	public void testJPQL() {
//		EntityManagerFactory emf = Persistence.createEntityManagerFactory("step03");
//		EntityManager em = emf.createEntityManager();
//		EntityTransaction tx = em.getTransaction();
//
//	
//		@Entity
//		public class sdasd{
//			@Id
//			@Column
//			@GeneratedValue(strategy = GenerationType.IDENTITY)
//			private Long id; // 객체지향에서 다루기 위한 id?
//			@Column(name = "origin_id")
//			private String origin_id;
//			
//		}
//		
//	TypedQuery<sdasd> query = em.createQuery(
//			"SELECT *");
//			
//	
//	}

	
	public static void typedQuery() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("step03");
		EntityManager em = emf.createEntityManager();
		String sqlquery = "SELECT * FROM student";
		TypedQuery<Student> realquery = em.createQuery(sqlquery, Student.class);
		
		List<Student> StudentList = realquery.getResultList();
		for( Student student : StudentList) {
			System.out.println(Student.class);
		}
		
		Major major1 = new Major("컴퓨터 공학");
		em.persist(major1);
		
		Student student1 = new Student("Yoo");
		student1.setMajor(major1); 
		em.persist(student1);
		
		Student student2 = new Student("Kang");
		student2.setMajor(major1);
		em.persist(student2); 
	}
	
	// ? 쓸수있음
}
