package com.example.alinadiplom.services;

import com.example.alinadiplom.DTO.CreateTaskDTO;
import com.example.alinadiplom.DTO.XMLTaskSendDTO;
import com.example.alinadiplom.exceptions.ResourceNotFoundException;
import com.example.alinadiplom.model.*;
import com.example.alinadiplom.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;

    @Autowired
    private RouteListService routeListService;

    @Autowired
    private PermissionDocumentService permissionDocumentService;

    @Autowired
    private StatusService statusService;

    @Autowired
    private TaskStatusService taskStatusService;

    @Autowired
    private WorkTypeService workTypeService;

    @Autowired
    private EmployeeService employeeService;

    @Transactional
    public XMLTaskSendDTO create(CreateTaskDTO task) {
        Task t = new Task();
        RouteList rl = routeListService.getByMlNumber(Math.toIntExact(task.getMlNumber()));
        PermissionDocument pd = permissionDocumentService.getByPrId(Math.toIntExact(task.getPdId()));
        Priority prior = Priority.valueOf(task.getPriorityId());
        WorkType wt = workTypeService.getById(task.getWtId());
        Employee employee = employeeService.getById(task.getAssigneeId());


        t.setComment(task.getComment());
        t.setDateOfCreation(new Date());
        t.setAddress(task.getAddress());
        t.setMlNumber(rl);
        t.setPdId(pd);
        t.setPriorityId(prior); // ✅ Исправлено
        t.setWtId(wt);
        t.setAssignee(employee);
        t.setTaskNumber(null);
        t = repository.save(t);

        // Добавление статуса "Запланировано"
        Status taskStatus;
        try {
            taskStatus = statusService.getAll()
                    .stream()
                    .filter(x -> Objects.equals(x.getStatusType(), "Запланировано"))
                    .findFirst()
                    .orElseThrow();
        } catch (Exception e) {
            System.out.println("Status \"Запланировано\" not found. Force adding it to DB.");
            Status planned = new Status();
            planned.setStatusType("Запланировано");
            taskStatus = statusService.create(planned);
        }

        // Добавление записи в смежную таблицу TaskStatus
        TaskStatus taskStatusEntry = new TaskStatus();
        Date date = new Date();
        taskStatusEntry.setDateOfStatusSet(date);
        taskStatusEntry.setTimeOfStatusSet(new Time(date.getTime()));
        taskStatusEntry.setTaskNumber(t);
        taskStatusEntry.setStatusId(taskStatus);
        taskStatusService.create(taskStatusEntry);


        XMLTaskSendDTO xmlTask = new XMLTaskSendDTO(t, rl, prior, pd, wt, employee);

        return xmlTask;
    }

    public List<Task> getAll() {
        return repository.findAll();
    }

    public Task getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found: " + id));
    }

    public Task update(Long id, Task newData) {
        Task task = getById(id);
        task.setAddress(newData.getAddress());
        task.setComment(newData.getComment());
        task.setDateOfCreation(newData.getDateOfCreation());
        task.setMlNumber(newData.getMlNumber());
        task.setPdId(newData.getPdId());
        task.setPriorityId(newData.getPriorityId());
        task.setWtId(newData.getWtId());
        return repository.save(task);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
