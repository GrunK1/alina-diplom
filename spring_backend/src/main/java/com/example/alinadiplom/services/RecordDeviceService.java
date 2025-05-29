package com.example.alinadiplom.services;

import com.example.alinadiplom.exceptions.ResourceNotFoundException;
import com.example.alinadiplom.model.PollRegistry;
import com.example.alinadiplom.model.RecordDevice;
import com.example.alinadiplom.repositories.PollRegistryRepository;
import com.example.alinadiplom.repositories.RecordDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecordDeviceService {

    @Autowired
    private RecordDeviceRepository repository;
    @Autowired
    PollRegistryRepository pollRegistryRepository;

    public RecordDevice create(RecordDevice device) {
        return repository.save(device);
    }

    public List<RecordDevice> getAll() {
        return repository.findAll();
    }

    public RecordDevice getBySerial(String serial) {
        return repository.findById(serial)
                .orElseThrow(() -> new ResourceNotFoundException("RecordDevice not found: " + serial));
    }

    public RecordDevice update(String serial, RecordDevice newData) {
        RecordDevice device = getBySerial(serial);
        device.setUspdSerialNumber(newData.getUspdSerialNumber());
        device.setPuModel(newData.getPuModel());
        device.setPuAddress(newData.getPuAddress());
        device.setPuCoordinates(newData.getPuCoordinates());
        return repository.save(device);
    }

    public void delete(String serial) {
        repository.deleteById(serial);
    }
}