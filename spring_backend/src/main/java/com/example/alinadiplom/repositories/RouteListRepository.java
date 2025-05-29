package com.example.alinadiplom.repositories;

import com.example.alinadiplom.model.RouteList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RouteListRepository extends JpaRepository<RouteList, Integer> {
    Optional<RouteList> findByMlNumber(Integer mlNumber);
}