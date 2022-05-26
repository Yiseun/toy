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
		 * 1. �а� ������ ����, ����
		 * 
		 * 2. �л� ������ ����(�л�1 Yoo, �л�2 Kang) �� ��ǻ�� ���а��� �������踦 ���� �� ����
		 */
		
		tx.begin();
		
		// 1.
		Major major1 = new Major("��ǻ�� ����");
		em.persist(major1);
		
		// 2-1. �л� 1 ����, ����
		Student student1 = new Student("Yoo");
		student1.setMajor(major1); // �������� ����
		em.persist(student1);
		
		// 2-2. �л�2 ����, ����
		Student student2 = new Student("Kang");
		student2.setMajor(major1); // �������� ����
		em.persist(student2); //Ŀ�ԵǱ������� �ϸ�ȴ� ���� �������ʿ����
		
		tx.commit();
		// SELECT * FROM student; major_id �� ����� �� Ȯ��
	}
	
	@Test
	public void readStudents() {// JPA�� �л� ����Ʈ ��ȸ
		//���������ü��ִµ� ĳ�ÿ� �����ִ� ������ó�� �̸� �������°� �׷��� ���ٸ� ������
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("step03");
		EntityManager em = emf.createEntityManager();
		
		Major major = em.find(Major.class, 1L); //���� ��ƼƼ�� ���� �л� ����Ʈ�� �����Դ�
//		System.out.println(major);
//		System.out.println(major.getStudents());
		
		List<Student> students = major.getStudents();
		for (Student student : students) {
			System.out.println(student.getStudentName());
		}
	}
	
	@Test
	public void testSaveNonOwnerWithJPA() { // JPA�� ������ ����� �������迡�� ���������� ������ �ƴ� ������ ���� �Է��� ���
		//student���̺��� ��ȸ������
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("step03");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		// �а� ������ ����
		Major major1 = new Major("��ǻ�� ����");
		em.persist(major1);
		
		// �л�1 ����
		Student student1 = new Student("Yoo");
//		student1.setMajor(major1); // �̰� ���� ���������� ������ ���� �������� ����
		
		//�л���ƼƼ�� �������輳���ϸ� ���ε� �ݴ�� �����غ��ڴ°�
		//���� ���������� ������ �ƴ� ������ �������踦 ������ ���,
		List<Student> students = major1.getStudents(); //�л��� ����Ʈ�� �����µ� ������ ���� ����ִ�
		//�̶� students���� ���� ���� ����ִ� ArrayList�� �ִ�
		students.add(student1); //�л��� ���� �ƴ϶� �а����� ������ �л��� ���Խ�Ų��?
		em.persist(student1);
		
		// �л�2 ����
		Student student2 = new Student("Kang");
		students.add(student2);
		em.persist(student2);
		
		//�ܷ�Ű�� �ִ����� ���������� �����̷��µ�
		//���������� ������ �ִ��ʿ��� ������Ѵ� ��? ����� ������
		
		tx.commit();
		
	}
	
	@Test
	public void testSaveOwnerWithOOP() { // OOP ������ ��ü���� �ڵ忡�� ���������� ����
		//��ü�����϶��� ���� �� �ؾ��Ѵ�
		//�л����׵� ��Ͻ��Ѿ��ϰ� �а����� ����Ѿ��ϰ�
		Major major1 = new Major("��ǻ�� ����");
		
		Student student1 = new Student("Yoo");
		student1.setMajor(major1); // ���������� ���� ���⿡�� �������� ����
		
		Student student2 = new Student("Kang");
		student2.setMajor(major1); // ���������� ���� ���⿡�� �������� ����
		
		List<Student> students = major1.getStudents();
		System.out.println("students.size : " + students.size());
		
	}
	
	@Test
	public void biWithPureOOP() { // OOP ������ ��ü���� �ڵ忡�� ����� �������� ����
		//��ü�����϶��� ���� �� �ؾ��Ѵ�
		//�л����׵� ��Ͻ��Ѿ��ϰ� �а����� ����Ѿ��ϰ�
		Major major1 = new Major("��ǻ�� ����");
		List<Student> students = major1.getStudents();
		
		Student student1 = new Student("Yoo");
		student1.setMajor(major1); // ���������� ���� ���⿡�� �������� ����
		students.add(student1); // �ݴ������� �������� ����
		
		Student student2 = new Student("Kang");
		student2.setMajor(major1); // ���������� ���� ���⿡�� �������� ����
		students.add(student2); // �ݴ������� �������� ����
		
		System.out.println("student.size:" + students.size());
	}
	
	@Test
	public void biWithJPA() { // JPA�� Ȱ���� ����� ����
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("step03");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		Major major1 = new Major("������");
		em.persist(major1);
		
		Student student1 = new Student("Park");
		
		// ����� �������� ����
		student1.setMajor(major1); // ���������� ���� ���⿡�� ����
		List<Student> students = major1.getStudents();
		students.add(student1); // �ݴ������� ����		
		em.persist(student1); // ����ȭ
		
		//�л�2�� ���� �� �������� ����, ����ȭ
		Student student2 = new Student("Kim");
		student2.setMajor(major1);
		students.add(student2);
		em.persist(student2);
		
		
		tx.commit();
		
		//���� ���� �Ẹ�°�
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
//			private Long id; // ��ü���⿡�� �ٷ�� ���� id?
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
		
		Major major1 = new Major("��ǻ�� ����");
		em.persist(major1);
		
		Student student1 = new Student("Yoo");
		student1.setMajor(major1); 
		em.persist(student1);
		
		Student student2 = new Student("Kang");
		student2.setMajor(major1);
		em.persist(student2); 
	}
	
	// ? ��������
}
