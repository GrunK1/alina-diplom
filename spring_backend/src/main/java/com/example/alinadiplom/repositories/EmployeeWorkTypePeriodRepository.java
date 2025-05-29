package com.example.alinadiplom.repositories;

import com.example.alinadiplom.model.EmployeeWorkTypePeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EmployeeWorkTypePeriodRepository extends JpaRepository<EmployeeWorkTypePeriod, Long> {
    List<EmployeeWorkTypePeriod> findByWorkPosition_PosIdAndDateOfEndWorkIsNull(Integer posId);
}
