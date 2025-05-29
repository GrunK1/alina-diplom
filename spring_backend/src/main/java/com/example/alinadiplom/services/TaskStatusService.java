package com.example.alinadiplom.services;

import com.example.alinadiplom.exceptions.ResourceNotFoundException;
import com.example.alinadiplom.model.TaskStatus;
import com.example.alinadiplom.repositories.TaskStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskStatusService {

    @Autowired
    private TaskStatusRepository repository;

    public TaskStatus create(TaskStatus taskStatus) {
        taskStatus.setStatusId(null);
        return repository.save(taskStatus);
    }

    public List<TaskStatus> getAll() {
        return repository.findAll();
    }

    public TaskStatus getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TaskStatus not found: " + id));
    }

    public TaskStatus update(Integer id, TaskStatus newData) {
        TaskStatus taskStatus = getById(id);
        taskStatus.setStatusId(newData.getStatusId());
        taskStatus.setTaskNumber(newData.getTaskNumber());
        taskStatus.setDateOfStatusSet(newData.getDateOfStatusSet());
        taskStatus.setTimeOfStatusSet(newData.getTimeOfStatusSet());
        return repository.save(taskStatus);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}