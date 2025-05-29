package com.example.alinadiplom.services;

import com.example.alinadiplom.exceptions.ResourceNotFoundException;
import com.example.alinadiplom.model.RouteList;
import com.example.alinadiplom.repositories.RouteListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteListService {

    @Autowired
    private RouteListRepository repository;

    public RouteList create(RouteList routeList) {
        return repository.save(routeList);
    }

    public List<RouteList> getAll() {
        return repository.findAll();
    }

    public RouteList getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RouteList not found: " + id));
    }
    public RouteList getByMlNumber(Integer mlNumber){
        return repository.findByMlNumber(mlNumber).orElseThrow(() -> new ResourceNotFoundException("Route list not found: "+mlNumber));
    }

    public RouteList update(Integer id, RouteList newData) {
        RouteList routeList = getById(id);
        routeList.setPlannedStartDate(newData.getPlannedStartDate());
        routeList.setPlannedEndDate(newData.getPlannedEndDate());
        routeList.setResponsibleOrganization(newData.getResponsibleOrganization());
        return repository.save(routeList);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}