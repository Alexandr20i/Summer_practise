package com.example.Summer_practise.service;

import com.example.Summer_practise.Filter.RequestSpecification;
import com.example.Summer_practise.models.Request;
import com.example.Summer_practise.models.RequestStatus;
import com.example.Summer_practise.repository.RequestRepository;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "requests";

    /** Создание нового запроса */
    public Request createRequest(Request request) {
        request.setStatus(RequestStatus.NEW);
        request.setCreation_date(LocalDateTime.now());
        kafkaTemplate.send(TOPIC, "Create request: " + request.getRequest_id());
        return requestRepository.save(request);
    }

    /** Получение списка запросов с фильтрами */
    public List<Request> getRequests (RequestStatus status, Long request_id, String inn, String executor, LocalDateTime startDate, LocalDateTime endDate) {
        RequestSpecification spec = new RequestSpecification(status, request_id, inn, executor, startDate, endDate);
        kafkaTemplate.send(TOPIC, "Get request: " + request_id);
        return requestRepository.findAll(spec);
    }

    /** Назначение запроса на исполнителя */
    public Request assignRequest(Long id, String executor) {
        Request request = requestRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Request not found"));
        if (!request.getStatus().equals(RequestStatus.NEW)) {
            // Логирование статуса перед исключением
            System.err.println("Attempting to assign request with ID " + id + " that is in status " + request.getStatus());
            throw new IllegalStateException("Cannot assign non-new request");
        }
        request.setExecutor(executor);
        request.setStatus(RequestStatus.ASSIGNED);
        kafkaTemplate.send(TOPIC, "Assign request: " + id);
        return requestRepository.save(request);
    }

    /** Закрытие запроса */
    public Request closeRequest(Long id, String response) {
        Request request = requestRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Request not found"));
        if (!request.getStatus().equals(RequestStatus.ASSIGNED)) {
            throw new IllegalStateException("Only assigned requests can be closed");
        }
        request.setResponse(response);
        request.setStatus(RequestStatus.CLOSED);
        request.setClosing_date(LocalDateTime.now());
        kafkaTemplate.send(TOPIC, "Closed request: " + id);
        return requestRepository.save(request);
    }


    /** Отклонение запроса */
    public Request rejectRequest(Long id, String response) {
        Request request = requestRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Request not found"));
        if (!request.getStatus().equals(RequestStatus.NEW)) {
            throw new IllegalStateException("Only New requests can be rejected");
        }
        request.setResponse(response);
        request.setStatus(RequestStatus.REJECTED);
        request.setClosing_date(LocalDateTime.now());
        kafkaTemplate.send(TOPIC, "Rejected request: " + id);
        return requestRepository.save(request);
    }

}
