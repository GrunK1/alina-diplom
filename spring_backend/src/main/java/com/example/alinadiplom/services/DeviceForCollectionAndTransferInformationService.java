package com.example.alinadiplom.services;

import com.example.alinadiplom.exceptions.ResourceNotFoundException;
import com.example.alinadiplom.model.DeviceForCollectionAndTransferInformation;
import com.example.alinadiplom.repositories.DeviceForCollectionAndTransferInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceForCollectionAndTransferInformationService {

    @Autowired
    private DeviceForCollectionAndTransferInformationRepository repository;

    public DeviceForCollectionAndTransferInformation create(DeviceForCollectionAndTransferInformation device) {
        return repository.save(device);
    }

    public List<DeviceForCollectionAndTransferInformation> getAll() {
        return repository.findAll();
    }

    public DeviceForCollectionAndTransferInformation getBySerial(String serial) {
        return repository.findById(serial)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found: " + serial));
    }

    public DeviceForCollectionAndTransferInformation update(String serial, DeviceForCollectionAndTransferInformation newData) {
        DeviceForCollectionAndTransferInformation device = getBySerial(serial);
        device.setUspdType(newData.getUspdType());
        device.setUspdAddress(newData.getUspdAddress());
        device.setUspdPlaceOfInstallation(newData.getUspdPlaceOfInstallation());
        device.setConnectionType(newData.getConnectionType());
        return repository.save(device);
    }

    public void delete(String serial) {
        repository.deleteById(serial);
    }
}