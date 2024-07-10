package com.example.Summer_practise.Tests;

import com.example.Summer_practise.models.Request;
import com.example.Summer_practise.models.RequestStatus;
import com.example.Summer_practise.repository.RequestRepository;
import com.example.Summer_practise.service.RequestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RequestServiceTests {
    @Autowired
    private RequestService requestService;

    @Autowired
    private RequestRepository requestRepository;

    @Test
    public void testCreateRequest() {
        Request request = new Request();
        request.setInn("123456789");
        request.setContact_name("John Doe");
        request.setContact_phone("123-456-7890");
        request.setDescription("Service request description");

        Request createdRequest = requestService.createRequest(request);
        assertNotNull(createdRequest.getRequest_id());
        assertEquals(RequestStatus.NEW, createdRequest.getStatus());
    }

    @Test
    public void testAssignRequest() {
        Request request = new Request();
        request.setInn("123456789");
        request.setContact_name("John Doe");
        request.setContact_phone("123-456-7890");
        request.setDescription("Service request description");
        request.setStatus(RequestStatus.NEW);
        request.setCreation_date(LocalDateTime.now());

        request = requestRepository.save(request);

        Request updatedRequest = requestService.assignRequest(request.getRequest_id(), "executorName");
        assertEquals(RequestStatus.ASSIGNED, updatedRequest.getStatus());
        assertEquals("executorName", updatedRequest.getExecutor());
    }

    @Test
    public void testCloseRequest() {
        Request request = new Request();
        request.setInn("123456789");
        request.setContact_name("John Doe");
        request.setContact_phone("123-456-7890");
        request.setDescription("Service request description");
        request.setStatus(RequestStatus.ASSIGNED);
        request.setCreation_date(LocalDateTime.now());
        request.setExecutor("executorName");

        request = requestRepository.save(request);

        Request updatedRequest = requestService.closeRequest(request.getRequest_id(), "Service completed successfully");
        assertEquals(RequestStatus.CLOSED, updatedRequest.getStatus());
        assertNotNull(updatedRequest.getClosing_date());
        assertEquals("Service completed successfully", updatedRequest.getResponse());
    }

    @Test
    public void testGetRequests() {
        Request request1 = new Request();
        request1.setInn("123456789");
        request1.setContact_name("John Doe");
        request1.setContact_phone("123-456-7890");
        request1.setDescription("First request description");
        request1.setStatus(RequestStatus.NEW);
        request1.setCreation_date(LocalDateTime.now().minusDays(1));

        requestRepository.save(request1);

        Request request2 = new Request();
        request2.setInn("987654321");
        request2.setContact_name("Jane Doe");
        request2.setContact_phone("098-765-4321");
        request2.setDescription("Second request description");
        request2.setStatus(RequestStatus.NEW);
        request2.setCreation_date(LocalDateTime.now().minusDays(2));

        requestRepository.save(request2);

        List<Request> requests = requestService.getRequests(RequestStatus.NEW, null, null, null, LocalDateTime.now().minusDays(3), LocalDateTime.now());
        assertEquals(3, requests.size());

    }
}
