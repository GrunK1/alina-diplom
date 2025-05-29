package com.example.alinadiplom.services;

import com.example.alinadiplom.exceptions.ResourceNotFoundException;
import com.example.alinadiplom.model.Employee;
import com.example.alinadiplom.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    public Employee create(Employee employee) {
        return repository.save(employee);
    }

    public List<Employee> getAll() {
        return repository.findAll();
    }

    public Employee getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + id));
    }

    public Employee update(Integer id, Employee newData) {
        Employee employee = getById(id);
        employee.setName(newData.getName());
        employee.setSecond_name(newData.getSecond_name());
        employee.setMiddle_name(newData.getMiddle_name());
        return repository.save(employee);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}