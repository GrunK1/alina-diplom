package com.example.alinadiplom.services;

import com.example.alinadiplom.exceptions.ResourceNotFoundException;
import com.example.alinadiplom.model.EmployeeTask;
import com.example.alinadiplom.repositories.EmployeeTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeTaskService {

    @Autowired
    private EmployeeTaskRepository repository;

    public EmployeeTask create(EmployeeTask employeeTask) {
        employeeTask.setId(null);
        return repository.save(employeeTask);
    }

    public List<EmployeeTask> getAll() {
        return repository.findAll();
    }

    public EmployeeTask getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EmployeeTask not found: " + id));
    }

    public EmployeeTask update(Long id, EmployeeTask newData) {
        EmployeeTask employeeTask = getById(id);
        employeeTask.setTaskNumber(newData.getTaskNumber());
        employeeTask.setEmpId(newData.getEmpId());
        employeeTask.setAssignDate(newData.getAssignDate());
        employeeTask.setTaskAuthor(newData.getTaskAuthor());
        return repository.save(employeeTask);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}