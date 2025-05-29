package com.example.alinadiplom.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "poll_registry_to_route_list")
public class PollRegistryToRouteList {
    @Id
    @GeneratedValue
    private Long id; // Добавляем простой первичный ключ

    @ManyToOne
    private PollRegistry prId;

    @ManyToOne
    private RouteList routeListNumber;

    private Date dateOfSendRouteList;
}