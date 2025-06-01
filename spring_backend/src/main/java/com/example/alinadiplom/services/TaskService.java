package com.example.alinadiplom.services;

import com.example.alinadiplom.DTO.CreateTaskDTO;
import com.example.alinadiplom.DTO.TaskShowDTO;
import com.example.alinadiplom.DTO.XMLTaskSendDTO;
import com.example.alinadiplom.exceptions.ResourceNotFoundException;
import com.example.alinadiplom.model.*;
import com.example.alinadiplom.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.*;

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

    @Autowired
    private RecordDeviceService recordDeviceService;

    @Autowired
    private PollRegistryService pollRegistryService;

    @Autowired
    private PollRegistryToRouteListService pollRegistryToRouteListService;

    @Transactional
    public XMLTaskSendDTO create(CreateTaskDTO task) {
        Task t = new Task();
        RouteList rl = routeListService.getByMlNumber(Math.toIntExact(task.getMlNumber()));
        PermissionDocument pd = permissionDocumentService.getByPrId(Math.toIntExact(task.getPdId()));
        WorkType wt = workTypeService.getById(task.getWtId());
        Employee employee = employeeService.getById(task.getAssigneeId());

        // Поиск последней записи по ПУ
        PollRegistry poll = pollRegistryService.getAll().stream()
                .filter(p -> p.getPuSerialNumber().getPuSerialNumber().equals(task.getPuSerialNumber()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("PollRegistry not found by serial: " + task.getPuSerialNumber()));

        RouteList routeList = pollRegistryToRouteListService.getAll().stream()
                .filter(link -> link.getPrId().equals(poll))
                .map(PollRegistryToRouteList::getRouteListNumber)
                .findFirst()
                .orElse(null);

        // Вычисление приоритета по дате опроса
        Priority priority = Priority.getPriority(
                (int) ((new Date().getTime() - poll.getPollDate().getTime()) / (1000 * 60 * 60 * 24))
        );

        t.setComment(task.getComment());
        t.setDateOfCreation(new Date());
        t.setAddress(task.getAddress());
        t.setMlNumber(routeList != null ? routeList : rl);
        t.setPdId(pd);
        t.setPriorityId(priority);
        t.setWtId(wt);
        t.setAssignee(employee);
        t.setTaskNumber(null);
        t.setPuSerialNumber(task.getPuSerialNumber());

        t = repository.save(t);

        // Установка статуса "Запланировано"
        Status taskStatus = statusService.getAll().stream()
                .filter(s -> "Запланировано".equals(s.getStatusType()))
                .findFirst()
                .orElseGet(() -> {
                    Status s = new Status();
                    s.setStatusType("Запланировано");
                    return statusService.create(s);
                });

        TaskStatus taskStatusEntry = new TaskStatus();
        Date date = new Date();
        taskStatusEntry.setDateOfStatusSet(date);
        taskStatusEntry.setTimeOfStatusSet(new Time(date.getTime()));
        taskStatusEntry.setTaskNumber(t);
        taskStatusEntry.setStatusId(taskStatus);

        taskStatusService.create(taskStatusEntry);

        return new XMLTaskSendDTO(t, rl, priority, pd, wt, employee);
    }

    public List<TaskShowDTO> getAll() {
    List<Task> finded = repository.findAll();
    List<TaskShowDTO> returning = new ArrayList<>();
    for (Task t:
        finded){
        System.out.println("INFO: task:"+t);
        Optional<TaskStatus> lastStatusOptional = taskStatusService.getAll().stream()
                .filter(ts -> ts.getTaskNumber().getTaskNumber().equals(t.getTaskNumber())) // фильтруем по номеру задачи
                .max(Comparator.comparing(TaskStatus::getDateOfStatusSet)
                        .thenComparing(TaskStatus::getTimeOfStatusSet)); // сортируем по самой поздней дате и времени

        TaskStatus lastTaskStatus = lastStatusOptional.orElse(null); // получаем последний статус или null

        System.out.println("INFO: taskstatus:"+lastTaskStatus);
        Status taskStatus = statusService.getAll().stream()
                .filter(s-> {
                    if (lastTaskStatus != null){
                        return Objects.equals(s.getStatusId(), lastTaskStatus.getStatusId().getStatusId());
                    }
                    else return false;
                }).findFirst().orElse(null);
        System.out.println("INFO: status:"+taskStatus);
        if (taskStatus == null){
            System.out.println("\n\nERROR: status is NULL!\n\n");
            taskStatus = statusService.getAll().stream()
                    .filter(s -> "Запланировано".equals(s.getStatusType()))
                    .findFirst()
                    .orElseGet(() -> {
                        Status s = new Status();
                        s.setStatusType("Запланировано");
                        return statusService.create(s);
                    });
            System.out.println("WARNING: new status is:"+taskStatus);
        }
        TaskShowDTO dto = new TaskShowDTO(
                t.getTaskNumber(),
                t.getAddress(),
                t.getComment(),
                t.getDateOfCreation(),
                t.getMlNumber(),
                t.getPdId(),
                t.getPriorityId(),
                t.getWtId(),
                t.getAssignee(),
                taskStatus.getStatusType(),
                t.getStartDate(),
                t.getEndDate(),
                t.getPuSerialNumber()
        );
        returning.add(dto);
    }
    return returning;
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
