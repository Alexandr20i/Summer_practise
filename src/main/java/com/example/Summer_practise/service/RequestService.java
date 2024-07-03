package com.example.Summer_practise.service;

import com.example.Summer_practise.models.Request;
import com.example.Summer_practise.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "requests";

    /** Создание нового запроса */
    public Request createRequest(Request request) {
        request.setRequest_id(UUID.randomUUID().toString());
        request.setStatus("NEW");
        request.setCreation_date(LocalDateTime.now());
        requestRepository.save(request);
        kafkaTemplate.send(TOPIC, "Created request: " + request.getRequest_id());
        return request;
    }

    /** Получение списка запросов с фильтрами */
    public List<Request> getRequests(Map<String, String> filters) {
        // Implement filtering logic
        return requestRepository.findAll();
    }

    /** Назначение запроса на исполнителя */
    public void assignRequest(String request_id, String executor) {
        Request request = requestRepository.findById(request_id).orElseThrow();
        request.setExecutor(executor);
        request.setStatus("IN_PROGRESS");
        requestRepository.save(request);
        kafkaTemplate.send(TOPIC, "Assigned request: " + request_id);
    }

    /** Закрытие запроса */
    public void closeRequest(String request_id, String response) {
        Request request = requestRepository.findById(request_id).orElseThrow();
        if (!"IN_PROGRESS".equals(request.getStatus()) || !request.getExecutor().equals(request.getExecutor())) {
            throw new IllegalStateException("Request not assigned to current executor.");
        }
        request.setStatus("CLOSED");
        request.setResponse(response);
        request.setClosing_date(LocalDateTime.now());
        requestRepository.save(request);
        kafkaTemplate.send(TOPIC, "Closed request: " + request_id);
    }

    /** Отклонение запроса */
    public void rejectRequest(String request_id, String response) {
        Request request = requestRepository.findById(request_id).orElseThrow();
        if (!"IN_PROGRESS".equals(request.getStatus()) || !request.getExecutor().equals(request.getExecutor())) {
            throw new IllegalStateException("Request not assigned to current executor.");
        }
        request.setStatus("REJECTED");
        request.setResponse(response);
        request.setClosing_date(LocalDateTime.now());
        requestRepository.save(request);
        kafkaTemplate.send(TOPIC, "Rejected request: " + request_id);
    }
}
