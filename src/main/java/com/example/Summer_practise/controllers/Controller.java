package com.example.Summer_practise.controllers;

import com.example.Summer_practise.models.Request;
import com.example.Summer_practise.service.RequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/request")
@Tag(name = "Request Controller", description = "API for managing service requests")
public class Controller {

    @Autowired
    private RequestService requestService;

    @PostMapping
    @Operation(summary = "Create a new service request")
    public ResponseEntity<?> createRequest(@RequestBody Request request) {
        Request createdRequest = requestService.createRequest(request);
        return ResponseEntity.ok(Collections.singletonMap("requestId", createdRequest.getRequest_id()));
    }


}
