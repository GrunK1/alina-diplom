package com.example.alinadiplom.DTO;

import jakarta.xml.bind.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@XmlRootElement(name = "routeList")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
public class XMLRouteListDTO {

    private Integer mlNumber;

    @XmlSchemaType(name = "dateTime")
    private Date plannedStartDate;

    @XmlSchemaType(name = "dateTime")
    private Date plannedEndDate;

    @XmlSchemaType(name = "dateTime")
    private Date pollDate;

    @XmlElementWrapper(name = "PUs")
    @XmlElement(name = "pu")
    private List<PUDTO> PUs;

    private String responsibleOrganization;

    @XmlAccessorType(XmlAccessType.FIELD)
    @Data
    @NoArgsConstructor
    public static class PUDTO {
        private String puSerialNumber;
        private Boolean pollFact;
        private String indications;
    }
}
