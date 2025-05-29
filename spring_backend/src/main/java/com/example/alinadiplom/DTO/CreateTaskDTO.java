package com.example.alinadiplom.DTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CreateTaskDTO {
    private String address;
    private String comment;
    private Long mlNumber;
    private Long pdId;
    private String priorityId;
    private Integer wtId;
    private Integer assigneeId;  // Добавленное поле
}
