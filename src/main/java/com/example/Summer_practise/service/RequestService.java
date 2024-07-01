package com.example.Summer_practise.service;

import com.example.Summer_practise.models.Request;
import com.example.Summer_practise.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    private static final String TOPIC = "requests";

    public Request createRequest(Request request) {
        request.setRequest_id(UUID.randomUUID().toString());
        request.setStatus("NEW");
        request.setCreation_date(LocalDateTime.now());
        requestRepository.save(request);
//        kafkaTemplate.send(TOPIC, "Created request: " + request.getRequestId());
        return request;
    }

    public List<Request> getRequests(Map<String, String> filters) {
        // Implement filtering logic
        return requestRepository.findAll();
    }

    public void assignRequest(String requestId, String executor) {
        Request request = requestRepository.findById(requestId).orElseThrow();
        request.setExecutor(executor);
        request.setStatus("IN_PROGRESS");
        requestRepository.save(request);
//        kafkaTemplate.send(TOPIC, "Assigned request: " + requestId);
    }

    public void closeRequest(String requestId, String response) {
        Request request = requestRepository.findById(requestId).orElseThrow();
        if (!"IN_PROGRESS".equals(request.getStatus()) || !request.getExecutor().equals(request.getExecutor())) {
            throw new IllegalStateException("Request not assigned to current executor.");
        }
        request.setStatus("CLOSED");
        request.setResponse(response);
        request.setClosing_date(LocalDateTime.now());
        requestRepository.save(request);
//        kafkaTemplate.send(TOPIC, "Closed request: " + requestId);
    }

    public void rejectRequest(String requestId, String response) {
        Request request = requestRepository.findById(requestId).orElseThrow();
        if (!"IN_PROGRESS".equals(request.getStatus()) || !request.getExecutor().equals(request.getExecutor())) {
            throw new IllegalStateException("Request not assigned to current executor.");
        }
        request.setStatus("REJECTED");
        request.setResponse(response);
        request.setClosing_date(LocalDateTime.now());
        requestRepository.save(request);
//        kafkaTemplate.send(TOPIC, "Rejected request: " + requestId);
    }
}
