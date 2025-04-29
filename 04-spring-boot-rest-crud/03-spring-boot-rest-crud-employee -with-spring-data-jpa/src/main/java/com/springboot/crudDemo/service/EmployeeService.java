package com.springboot.crudDemo.service;

import com.springboot.crudDemo.entity.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> findsAll();

    Employee findById(int theId);

    Employee save(Employee theEmployee);

    void deleteById(int theId);
}
