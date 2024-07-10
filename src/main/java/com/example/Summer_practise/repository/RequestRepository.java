package com.example.Summer_practise.repository;

import com.example.Summer_practise.models.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RequestRepository extends JpaRepository<Request, Long>, JpaSpecificationExecutor<Request>
{
}

