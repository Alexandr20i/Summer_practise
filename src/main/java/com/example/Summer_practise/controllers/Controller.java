package com.example.Summer_practise.controllers;

import com.example.Summer_practise.models.Request;
import com.example.Summer_practise.service.RequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/request")
@Tag(name = "Request Controller", description = "API for managing service requests")
public class Controller {

    @Autowired
    private RequestService requestService;

    @PostMapping
    @Operation(summary = "Create a new service request")
    public ResponseEntity<?> createRequest(@RequestBody Request request) {

        // Добавляем логирование
        System.out.println("Received request: " + request);

        Request createdRequest = requestService.createRequest(request);
        return ResponseEntity.ok(Collections.singletonMap("request_id", createdRequest.getRequest_id()));
    }

    @GetMapping
    @Operation(summary = "Get service requests with filters")
    public ResponseEntity<?> getRequests(@Parameter(description = "Filter by status") @RequestParam(required = false) String status,
                                         @Parameter(description = "Filter by creation date") @RequestParam(required = false) String creation_date,
                                         @Parameter(description = "Filter by closing date") @RequestParam(required = false) String closing_date,
                                         @Parameter(description = "Filter by executor") @RequestParam(required = false) String executor,
                                         @Parameter(description = "Filter by INN") @RequestParam(required = false) String inn,
                                         @Parameter(description = "Filter by request ID") @RequestParam(required = false) String request_id){




        List<Request> requests = requestService.getRequests(Map.of(
                "status", status,
                "creation_date", creation_date,
                "closing_date", closing_date,
                "executor", executor,
                "inn", inn,
                "request_id", request_id));

        // Добавляем логирование
        System.out.println("Creating request: " + requests);

        return ResponseEntity.ok(requests);

    }


}
