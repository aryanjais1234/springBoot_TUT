package com.springBoot.cruddemo.dao;

import com.springBoot.cruddemo.entity.Student;

import java.util.List;

public interface StudentDAo {
    void save(Student student);

    Student findById(Integer id);

    List<Student> findAll();

    List<Student> findByLastName(String theLastName);

    void update(Student theStudent);

    void delete(Integer id);

    int deleteAll();
}
