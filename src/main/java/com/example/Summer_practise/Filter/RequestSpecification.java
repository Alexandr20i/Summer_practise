package com.example.Summer_practise.Filter;

import com.example.Summer_practise.models.Request;
import com.example.Summer_practise.models.RequestStatus;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RequestSpecification implements Specification<Request> {

    private final RequestStatus status;
    private final Long request_id;
    private final String inn;
    private final String executor;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    public RequestSpecification(RequestStatus status, Long request_id, String inn, String executor, LocalDateTime startDate, LocalDateTime endDate) {
        this.status = status;
        this.request_id = request_id;
        this.inn = inn;
        this.executor = executor;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public Predicate toPredicate(Root<Request> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (status != null) {
            predicates.add(criteriaBuilder.equal(root.get("status"), status));
        }
        if (request_id != null) {
            predicates.add(criteriaBuilder.equal(root.get("request_id"), request_id));
        }
        if (inn != null && !inn.isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("inn"), inn));
        }
        if (executor != null && !executor.isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("executor"), executor));
        }
        if (startDate != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("creation_date"), startDate));
        }
        if (endDate != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("creation_date"), endDate));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
