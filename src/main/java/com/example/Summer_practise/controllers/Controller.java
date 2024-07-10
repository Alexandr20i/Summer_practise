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

    @PostMapping
    @Operation(summary = "Create a new service request")
    public ResponseEntity<Request> createRequest(@RequestBody Request request) {
        Request createdRequest = requestService.createRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
    }

    @GetMapping
    @Operation(summary = "Get all service request")
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

    @PatchMapping("/{id}/assign")
    @Operation(summary = "Get all service request")
    public ResponseEntity<Request> assignRequest(@PathVariable Long id, @RequestParam String executor) {
        try {
            Request request = requestService.assignRequest(id, executor);
            return ResponseEntity.ok(request);
        } catch (IllegalStateException e) {
            // Логирование ошибки
            System.err.println("Error in assignRequest: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PatchMapping("/{id}/close")
    public ResponseEntity<Request> closeRequest(@PathVariable Long id, @RequestParam String response) {
        Request request = requestService.closeRequest(id, response);
        return ResponseEntity.ok(request);
    }

    @PatchMapping("/{id}/reject")
    public ResponseEntity<Request> rejectRequest(@PathVariable Long id, @RequestParam String response) {
        Request request = requestService.rejectRequest(id, response);
        return ResponseEntity.ok(request);
    }
}
