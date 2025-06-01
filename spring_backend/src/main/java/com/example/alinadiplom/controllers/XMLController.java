package com.example.alinadiplom.controllers;

import com.example.alinadiplom.DTO.XMLRouteListDTO;
import com.example.alinadiplom.DTO.XMLTaskStatusUpdateDTO;
import com.example.alinadiplom.model.PollRegistry;
import com.example.alinadiplom.model.PollRegistryToRouteList;
import com.example.alinadiplom.model.RouteList;
import com.example.alinadiplom.model.TaskStatus;
import com.example.alinadiplom.services.*;
import jakarta.transaction.Transactional;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Time;
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping("/api/xml")
public class XMLController {
    @Autowired
    private RouteListService routeListService;
    @Autowired
    private RecordDeviceService recordDeviceService;
    @Autowired
    private PollRegistryService pollRegistryService;
    @Autowired
    private PollRegistryToRouteListService pollRegistryToRouteListService;
    @Autowired
    private TaskStatusService taskStatusService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private StatusService statusService;

    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE // Явное указание типа контента
    )
    @Transactional
    public ResponseEntity<String> uploadFile(
            @RequestPart("file") MultipartFile file) throws IOException, JAXBException
    {
        Date dateOfSend = new Date();

        System.out.println(file);
        System.out.println("size: "+file.getSize());
        System.out.println("content_type: "+file.getContentType());
        System.out.println("filename: "+file.getName());
        System.out.println("orig_filename: "+file.getOriginalFilename());
        System.out.println("input_stream: "+file.getInputStream());
        System.out.println("resource: "+file.getResource());
        if (!Objects.equals(file.getContentType(), "text/xml")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("content type "+file.getContentType()+" not supported. try text/xml");
        }
//        System.out.println("bytes: "+ Arrays.toString(file.getBytes()));
        // Правильное чтение XML
        JAXBContext context = JAXBContext.newInstance(XMLRouteListDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        XMLRouteListDTO routeList = (XMLRouteListDTO) unmarshaller.unmarshal(file.getInputStream());
        System.out.println("route list DTO: "+routeList);

        // добавление кучи говна из XML
        RouteList rl = new RouteList();
        rl.setMlNumber(routeList.getMlNumber());
        rl.setPlannedStartDate(routeList.getPlannedStartDate());
        rl.setPlannedEndDate(routeList.getPlannedEndDate());
        rl.setResponsibleOrganization(routeList.getResponsibleOrganization());
        rl = routeListService.create(rl);

        for (XMLRouteListDTO.PUDTO pu:
                routeList.getPUs()){
            PollRegistry pr = new PollRegistry();
            pr.setIndications(pu.getIndications());
            pr.setPuSerialNumber(recordDeviceService.getBySerial(pu.getPuSerialNumber()));
            pr.setPollDate(routeList.getPollDate());
            pr.setPollFact(pu.getPollFact());
            pr = pollRegistryService.create(pr);

//            Integer createdPrId = pr.getPrId();
            PollRegistryToRouteList prToRl = new PollRegistryToRouteList();
            prToRl.setPrId(pr);
            prToRl.setDateOfSendRouteList(dateOfSend);
            prToRl.setRouteListNumber(rl);
            prToRl = pollRegistryToRouteListService.create(prToRl);
        }

        return ResponseEntity.ok("Файл получен. Данные записаны. Размер: " + file.getSize());
    }

    @PostMapping(
            value = "/uploadTaskUpdate",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE // Явное указание типа контента
    )
    @Transactional
    public ResponseEntity<String> uploadTaskUpdateFile(
            @RequestPart("file") MultipartFile file) throws Exception {
        Date dateOfSend = new Date();

        System.out.println(file);
        System.out.println("size: "+file.getSize());
        System.out.println("content_type: "+file.getContentType());
        System.out.println("filename: "+file.getName());
        System.out.println("orig_filename: "+file.getOriginalFilename());
        System.out.println("input_stream: "+file.getInputStream());
        System.out.println("resource: "+file.getResource());

        if (!Objects.equals(file.getContentType(), "text/xml")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("content type "+file.getContentType()+" not supported. try text/xml");
        }

        JAXBContext context = JAXBContext.newInstance(XMLTaskStatusUpdateDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        XMLTaskStatusUpdateDTO taskStatusUpdateDTO = (XMLTaskStatusUpdateDTO) unmarshaller.unmarshal(file.getInputStream());
        System.out.println("taskStatusUpdateDTO: "+taskStatusUpdateDTO);
        TaskStatus ts = new TaskStatus();
        var task = taskService.getById(Long.valueOf(taskStatusUpdateDTO.getTaskId()));
        if (task == null){
            System.out.println("ERROR: Task with id="+taskStatusUpdateDTO.getTaskId()+" not found!");
            throw new Exception("ERROR: Task with id="+taskStatusUpdateDTO.getTaskId()+" not found!");
        }
        ts.setTaskNumber(task);
        var status = statusService.getById(taskStatusUpdateDTO.getNewTaskStatus());
        if (status == null){
            System.out.println("ERROR: Status with id="+taskStatusUpdateDTO.getNewTaskStatus()+" not found!");
            throw new Exception("ERROR: Status with id="+taskStatusUpdateDTO.getNewTaskStatus()+" not found!");
        }
        ts.setStatusId(status);
        ts.setDateOfStatusSet(dateOfSend);
        ts.setTimeOfStatusSet(new Time(dateOfSend.getTime()));

        taskStatusService.create(ts);

        return ResponseEntity.ok("Файл получен. Данные записаны. Размер: " + file.getSize());
    }
}
