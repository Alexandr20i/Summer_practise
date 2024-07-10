package com.example.Summer_practise.controllers;

import com.example.Summer_practise.models.Request;
import com.example.Summer_practise.models.RequestStatus;
import com.example.Summer_practise.service.RequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/request")
@Tag(name = "Request Controller", description = "API for managing service requests")
public class Controller {

    @Autowired
    private RequestService requestService;

//    @PostMapping
//    @Operation(summary = "Create a new service request")
//    public ResponseEntity<Request> createRequest(@RequestBody Request request){
//        return new ResponseEntity<>(requestService.createRequest(request), HttpStatus.CREATED);
//    }

    @PostMapping
    @Operation(summary = "Create a new service request")
    public ResponseEntity<Request> createRequest(@RequestBody Request request) {
        Request createdRequest = requestService.createRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
    }

    @GetMapping
    @Operation(summary = "Get all service request")
//    public ResponseEntity<List<Request>> getAllRequests(
//            @RequestParam(required = false) RequestStatus status,
//            @RequestParam(required = false) String inn,
//            @RequestParam(required = false) String executor,
//            @RequestParam(required = false) LocalDateTime startDate,
//            @RequestParam(required = false) LocalDateTime endDate) {
//        List<Request> requests = requestService.getRequests(status, inn, executor, startDate, endDate);
//        return ResponseEntity.ok(requests);
//    }
    public ResponseEntity<List<Request>> getAllRequests(
            @RequestParam(required = false) RequestStatus status,
            @RequestParam(required = false) Long request_id,
            @RequestParam(required = false) String inn,
            @RequestParam(required = false) String executor,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<Request> requests = requestService.getRequests(status, request_id, inn, executor, startDate, endDate);
        return ResponseEntity.ok(requests);
    }

//    @PostMapping
//    public ResponseEntity<Request> createRequest(@RequestBody Request request) {
//        if (request.getContact_name() == null) {
//            return ResponseEntity.badRequest().body(null);  // Или другой подход к обработке ошибок
//        }
//        return new ResponseEntity<>(requestService.createRequest(request), HttpStatus.CREATED);
//    }





//    @PostMapping
//    @Operation(summary = "Create a new service request")
//    public ResponseEntity<?> createRequest(@RequestBody Request request) {
//
//        // Добавляем логирование
//        System.out.println("Received request: " + request);
//
//        Request createdRequest = requestService.createRequest(request);
//        return ResponseEntity.ok(Collections.singletonMap("request_id", createdRequest.getRequest_id()));
//    }
//
//    @GetMapping
//    @Operation(summary = "Получить список запросов на услугу с возможностью фильтрации")
//    public ResponseEntity<?> getRequests(@Parameter(description = "Filter by status") @RequestParam(required = false) String status,
//                                         @Parameter(description = "Filter by creation date") @RequestParam(required = false) String creation_date,
//                                         @Parameter(description = "Filter by closing date") @RequestParam(required = false) String closing_date,
//                                         @Parameter(description = "Filter by executor") @RequestParam(required = false) String executor,
//                                         @Parameter(description = "Filter by INN") @RequestParam(required = false) String inn,
//                                         @Parameter(description = "Filter by request ID") @RequestParam(required = false) String request_id){
//
//        List<Request> requests = requestService.getRequests(Map.of(
//                "status", status,
//                "creation_date", creation_date,
//                "closing_date", closing_date,
//                "executor", executor,
//                "inn", inn,
//                "request_id", request_id));
//
//        // Добавляем логирование
//        System.out.println("Creating request: " + requests);
//
//        return ResponseEntity.ok(requests);
//    }

//    @GetMapping
//    @Operation(summary = "Получить список запросов на услугу")
//    public



}
