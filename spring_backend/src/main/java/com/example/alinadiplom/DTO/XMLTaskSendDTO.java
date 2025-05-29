package com.example.alinadiplom.DTO;

import com.example.alinadiplom.model.*;
import com.example.alinadiplom.services.EmployeeService;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@XmlRootElement(name="task")
@Data
@RequiredArgsConstructor
public class XMLTaskSendDTO {
    Long taskId;
    String address;
    String comment;
    String priority;
    Long routeListNumber;
    String permissionDocumentType;
    String workType;
    String assigneeEmployeeFullName;

    public XMLTaskSendDTO(Task task, RouteList routeList, Priority priority, PermissionDocument permissionDocument, WorkType workType, Employee employee){
        this.taskId = task.getTaskNumber();
        this.address = task.getAddress();
        this.comment = task.getComment();
        this.priority = priority.getTitle();
        this.routeListNumber = (long) routeList.getMlNumber();
        this.permissionDocumentType = permissionDocument.getPdType();
        this.workType =  workType.getWtId() + ": " + workType.getWtType();
        if (employee.getMiddle_name() != null){
            this.assigneeEmployeeFullName = employee.getSecond_name() + " " + employee.getName() + " " + employee.getMiddle_name();
        } else {
            this.assigneeEmployeeFullName = employee.getSecond_name() + " " + employee.getName();
        }
    }
}
