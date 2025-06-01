package com.example.alinadiplom.DTO;


import com.example.alinadiplom.model.*;

import java.util.Date;

public record TaskShowDTO(
    Long taskNumber,
    String address,
    String comment,
    Date dateOfCreation,
    RouteList mlNumber,
    PermissionDocument pdId,
    Priority priorityId,
    WorkType wtId,
    Employee assignee,
    String status,
    Date startDate,
    Date endDate,
    String puSerialNumber
) {
}
