package com.example.alinadiplom.services;

import com.example.alinadiplom.exceptions.ResourceNotFoundException;
import com.example.alinadiplom.model.EmployeeWorkTypePeriod;
import com.example.alinadiplom.repositories.EmployeeWorkTypePeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeWorkTypePeriodService {

    @Autowired
    private EmployeeWorkTypePeriodRepository repository;

    public EmployeeWorkTypePeriod create(EmployeeWorkTypePeriod period) {
        period.setId(null);
        return repository.save(period);
    }

    public List<EmployeeWorkTypePeriod> getAll() {
        return repository.findAll();
    }

    public EmployeeWorkTypePeriod getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EmployeeWorkTypePeriod not found: " + id));
    }

    public EmployeeWorkTypePeriod update(Long id, EmployeeWorkTypePeriod newData) {
        EmployeeWorkTypePeriod period = getById(id);
        period.setEmpId(newData.getEmpId());
        period.setWorkPosition(newData.getWorkPosition());
        period.setDateOfStartWork(newData.getDateOfStartWork());
        period.setDateOfEndWork(newData.getDateOfEndWork());
        return repository.save(period);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}