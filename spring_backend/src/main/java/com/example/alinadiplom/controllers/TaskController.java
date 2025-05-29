package com.example.alinadiplom.controllers;

import com.example.alinadiplom.DTO.CreateTaskDTO;
import com.example.alinadiplom.DTO.XMLTaskSendDTO;
import com.example.alinadiplom.model.Task;
import com.example.alinadiplom.services.TaskService;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService service;

    @PostMapping
    public ResponseEntity<byte[]> create(@RequestBody CreateTaskDTO task) throws JAXBException, UnsupportedEncodingException {
        XMLTaskSendDTO xmlCreatedTask = service.create(task);
        JAXBContext context = JAXBContext.newInstance(XMLTaskSendDTO.class);
        Marshaller marshaller = context.createMarshaller();
        StringWriter sw = new StringWriter();
        marshaller.marshal(xmlCreatedTask, sw);

        byte[] xmlBytes = sw.toString().getBytes("UTF-8");
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=task_"+xmlCreatedTask.getTaskId()+".xml");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/xml");
        return ResponseEntity.ok().headers(headers).body(xmlBytes);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> update(@PathVariable Long id, @RequestBody Task task) {
        return ResponseEntity.ok(service.update(id, task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}