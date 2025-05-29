package com.example.alinadiplom.repositories;

import com.example.alinadiplom.model.PollRegistryToRouteList;
import com.example.alinadiplom.model.RouteList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PollRegistryToRouteListRepository extends JpaRepository<PollRegistryToRouteList, Long> {
    Optional<PollRegistryToRouteList> findTopByRouteListNumberOrderByDateOfSendRouteListDesc(RouteList routeList);
}
