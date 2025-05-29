package com.example.alinadiplom.services;

import com.example.alinadiplom.exceptions.ResourceNotFoundException;
import com.example.alinadiplom.model.PermissionDocument;
import com.example.alinadiplom.repositories.PermissionDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionDocumentService {

    @Autowired
    private PermissionDocumentRepository repository;

    public PermissionDocument create(PermissionDocument document) {
        return repository.save(document);
    }

    public List<PermissionDocument> getAll() {
        return repository.findAll();
    }

    public PermissionDocument getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PermissionDocument not found: " + id));
    }
    public PermissionDocument getByPrId(Integer prId){
        return repository.findByPdId(prId).orElseThrow(() -> new ResourceNotFoundException("PermissionDocument not found: " + prId));
    }

    public PermissionDocument update(Integer id, PermissionDocument newData) {
        PermissionDocument document = getById(id);
        document.setPdType(newData.getPdType());
        document.setPdDescription(newData.getPdDescription());
        return repository.save(document);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}