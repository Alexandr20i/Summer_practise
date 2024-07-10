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
import java.util.UUID;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;

//    private static final String TOPIC = "requests";

    /** Создание нового запроса */
    public Request createRequest(Request request) {
        request.setStatus(RequestStatus.NEW);
        request.setCreation_date(LocalDateTime.now());
        return requestRepository.save(request);
    }

    /** Получение списка запросов с фильтрами */
    public List<Request> getRequests (RequestStatus status, Long request_id, String inn, String executor, LocalDateTime startDate, LocalDateTime endDate) {
        RequestSpecification spec = new RequestSpecification(status, request_id, inn, executor, startDate, endDate);
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
        return requestRepository.save(request);
    }

    /** Закрытие запроса */
//    public void closeRequest(String request_id, String response) {
//        Request request = requestRepository.findById(request_id).orElseThrow();
//        if (!"IN_PROGRESS".equals(request.getStatus()) || !request.getExecutor().equals(request.getExecutor())) {
//            throw new IllegalStateException("Request not assigned to current executor.");
//        }
//        request.setStatus("CLOSED");
//        request.setResponse(response);
//        request.setClosing_date(LocalDateTime.now());
//        requestRepository.save(request);
//        kafkaTemplate.send(TOPIC, "Closed request: " + request_id);
//    }

    /** Отклонение запроса */
//    public void rejectRequest(String request_id, String response) {
//        Request request = requestRepository.findById(request_id).orElseThrow();
//        if (!"IN_PROGRESS".equals(request.getStatus()) || !request.getExecutor().equals(request.getExecutor())) {
//            throw new IllegalStateException("Request not assigned to current executor.");
//        }
//        request.setStatus("REJECTED");
//        request.setResponse(response);
//        request.setClosing_date(LocalDateTime.now());
//        requestRepository.save(request);
//        kafkaTemplate.send(TOPIC, "Rejected request: " + request_id);
//    }
}
