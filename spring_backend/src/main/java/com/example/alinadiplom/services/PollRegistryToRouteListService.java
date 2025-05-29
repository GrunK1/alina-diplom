package com.example.alinadiplom.services;

import com.example.alinadiplom.exceptions.ResourceNotFoundException;
import com.example.alinadiplom.model.PollRegistryToRouteList;
import com.example.alinadiplom.repositories.PollRegistryToRouteListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PollRegistryToRouteListService {

    @Autowired
    private PollRegistryToRouteListRepository repository;

    public PollRegistryToRouteList create(PollRegistryToRouteList pollRegistryToRouteList) {
        pollRegistryToRouteList.setId(null);
        return repository.save(pollRegistryToRouteList);
    }

    public List<PollRegistryToRouteList> getAll() {
        return repository.findAll();
    }

    public PollRegistryToRouteList getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PollRegistryToRouteList not found: " + id));
    }

    public PollRegistryToRouteList update(Long id, PollRegistryToRouteList newData) {
        PollRegistryToRouteList pollRegistryToRouteList = getById(id);
        pollRegistryToRouteList.setPrId(newData.getPrId());
        pollRegistryToRouteList.setRouteListNumber(newData.getRouteListNumber());
        pollRegistryToRouteList.setDateOfSendRouteList(newData.getDateOfSendRouteList());
        return repository.save(pollRegistryToRouteList);
    }



    public void delete(Long id) {
        repository.deleteById(id);
    }
}