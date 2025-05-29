package com.example.alinadiplom.repositories;

import com.example.alinadiplom.model.PollRegistry;
import com.example.alinadiplom.model.RecordDevice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PollRegistryRepository extends JpaRepository<PollRegistry, Integer> {
    Optional<PollRegistry> findTopByPuSerialNumber_PuSerialNumberOrderByPollDateDesc(String serial);
}
