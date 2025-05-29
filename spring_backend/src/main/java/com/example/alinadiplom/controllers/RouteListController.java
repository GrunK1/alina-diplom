package com.example.alinadiplom.controllers;

import com.example.alinadiplom.DTO.PUPoll;
import com.example.alinadiplom.DTO.RouteListDTO;
import com.example.alinadiplom.DTO.XMLRouteListDTO;
import com.example.alinadiplom.model.PollRegistry;
import com.example.alinadiplom.model.PollRegistryToRouteList;
import com.example.alinadiplom.model.Priority;
import com.example.alinadiplom.model.RouteList;
import com.example.alinadiplom.repositories.PollRegistryRepository;
import com.example.alinadiplom.repositories.PollRegistryToRouteListRepository;
import com.example.alinadiplom.repositories.RouteListRepository;
import com.example.alinadiplom.services.RouteListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.Duration;
import java.time.Instant;
import java.util.*;

@RestController
@RequestMapping("/api/route-lists")
public class RouteListController {

    @Autowired
    private RouteListService service;
    @Autowired
    private RouteListRepository repository;
    @Autowired
    private PollRegistryToRouteListRepository prToRlRepository;

    @PostMapping
    public ResponseEntity<RouteList> create(@RequestBody RouteList routeList) {
        return new ResponseEntity<>(service.create(routeList), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RouteList>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RouteList> get(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RouteList> update(@PathVariable Integer id, @RequestBody RouteList routeList) {
        return ResponseEntity.ok(service.update(id, routeList));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/enriched")
    public List<RouteListDTO> getEnrichedRouteLists() {
        List<RouteList> routeLists = repository.findAll();
        List<RouteListDTO> returnList = new ArrayList<>();
        for (RouteList rl:
            routeLists){
            Integer mlNumber = rl.getMlNumber();
            Date plannedStartDate = rl.getPlannedStartDate();
            Date plannedEndDate = rl.getPlannedEndDate();
            String responsibleOrganization = rl.getResponsibleOrganization();
            List<PollRegistryToRouteList> prToRlList = prToRlRepository.findAll().stream().filter(x->x.getRouteListNumber()==rl).toList();
            List<PUPoll> puPollList = new ArrayList<>();
            Date pollDate = new Date();
            for (PollRegistryToRouteList prToRl:
                prToRlList){
                long days = Duration.between(
                        prToRl.getPrId().getPollDate().toInstant(),
                        Instant.now()
                ).toDays();

                String priority = Priority.getPriority((int) days).getTitle();

                pollDate = prToRl.getPrId().getPollDate();
                puPollList.add(new PUPoll(prToRl.getPrId().getPuSerialNumber().getPuSerialNumber(),
                        priority));
            }
            RouteListDTO dto = new RouteListDTO(mlNumber, plannedStartDate, plannedEndDate, pollDate, puPollList);
            returnList.add(dto);
        }
        return returnList;

//        return routeLists.stream()
//                .map(routeList ->
//                {
//                    Optional<PollRegistryToRouteList> link = prToRlRepository
//                            .findTopByRouteListNumberOrderByDateOfSendRouteListDesc(routeList);
//
//                    if (link.isEmpty()) return null;
//
//                    PollRegistry poll = link.get().getPrId();
//
//                    long days = Duration.between(
//                            poll.getPollDate().toInstant(),
//                            Instant.now()
//                    ).toDays();
//
//                    String priority;
//                    if (days <= 6) priority = "Низкий";
//                    else if (days <= 14) priority = "Средний";
//                    else if (days <= 28) priority = "Средний+";
//                    else if (days <= 45) priority = "Высокий";
//                    else priority = "Критический";
//
//                    return new RouteListDto(
//                            routeList.getMlNumber(),
//                            routeList.getPlannedStartDate(),
//                            routeList.getPlannedEndDate(),
//                            poll.getPollDate(),
//                            poll.getPuSerialNumber().getPuSerialNumber(),
//                            priority
//                    );
//                })
//                .filter(Objects::nonNull)
//                .toList();
    }
}