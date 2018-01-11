package com.yrp.hibernate.bean;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;


public class MainTest {
    Configuration config;
    Session session;

    @Before
    public void setUp() throws Exception {
        config = new Configuration().configure();
        SessionFactory sessionFactory = config.buildSessionFactory(new ServiceRegistryBuilder()
                .applySettings(config.getProperties()).buildServiceRegistry());
        session = sessionFactory.openSession();
        session.beginTransaction();

    }

    @After
    public void tearDown() throws Exception {
        session.close();
    }

//    注意course不能重复，会报错Duplicate entry 'java进阶' for key 'UK_topojlrn80k8ohbvly4jj7t6f'
    @Test
    public void addCourse(){
        Course course=new Course();
        course.setName("java进阶");
        Course course1=new Course();
        course1.setName("编程思想");
        session.save(course);
//        session.save(course1);
        session.getTransaction().commit();
    }

    @Test
    public void addStudent(){
        Student student=new Student("安娜","女",23);
        session.save(student);
        session.getTransaction().commit();
    }
    @Test
    public void choiceCourse(){
        Set<Course> courses=new HashSet<>();

        Student student= (Student) session.get(Student.class,1);
        student.setCourses(courses);

        Course course= (Course) session.get(Course.class,1);
        courses.add(course);
//        courses.add((Course) session.get(Course.class,2));
//        session.save(courses);
//        学生选课
        student.getCourses().add(course);
        student.getCourses().add((Course) session.get(Course.class,2));

//        课程加入学生
        course.setStudents(new HashSet<Student>());
        course.getStudents().add((Student) session.get(Student.class,2));
//？？？？？？？？？？？？？？？？？？？？先保存，才能添加？？？？？？？？？？？？？？？？？？？？？？？？？？？？？
        Student student1=new Student("熊大","男",20);
        session.save(student1);
        course.getStudents().add(student1);
//????????????????????????????????????????????????????????????
        course.getStudents().add((Student) session.get(Student.class,3));

        session.getTransaction().commit();

//        Student s1=new Student();
//        s1.setName("lisi");
//        Course c1=new Course();
//        c1.setName("English");
//        Course c2=new Course();
//        c2.setName("science");
//        s1.setCourses(new HashSet<Course>());
//        c1.setStudents(new HashSet<Student>());
//        c2.setStudents(new HashSet<Student>());
//        s1.getCourses().add(c1);
//        s1.getCourses().add(c2);
//        c1.getStudents().add(s1);
//        c2.getStudents().add(s1);
//
//        session.save(c1);
//        session.save(s1);
//        session.getTransaction().commit();
    }

//        无效啊？？？？？？？？？？？？？？？？？？
    @Test
    public void deleteStudent(){
        Student student= (Student) session.get(Student.class,1);
        session.delete(student);
    }

    @Test
    public void getAllCourse(){
        Student student= (Student) session.get(Student.class,1);
        Set<Course> courses=student.getCourses();
        for (Course cours : courses) {
            System.out.println(cours.getName());
        }
    }
}