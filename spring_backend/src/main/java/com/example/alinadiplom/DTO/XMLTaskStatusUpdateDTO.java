package com.example.alinadiplom.DTO;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@XmlRootElement(name="taskStatusUpdate")
@Data
@RequiredArgsConstructor
public class XMLTaskStatusUpdateDTO {
    Integer taskId;
    Integer newTaskStatus;
}
