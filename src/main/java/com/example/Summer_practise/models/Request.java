package com.example.Summer_practise.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Request {
    @Id
    private String          request_id;
    private LocalDateTime   closing_date;
    private String          contact_name;
    private String             contact_phone;
    private LocalDateTime   creation_date;
    private String          description;
    private String          executor;
    private String             inn;
    private String          response;
    private String          status;
}
