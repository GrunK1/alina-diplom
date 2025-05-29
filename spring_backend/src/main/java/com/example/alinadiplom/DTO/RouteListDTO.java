package com.example.alinadiplom.DTO;

import java.util.Date;
import java.util.List;


public record RouteListDTO (
    Integer mlNumber,
    Date plannedStartDate,
    Date plannedEndDate,
    Date pollDate,
    List<PUPoll> puPolls
) {}

