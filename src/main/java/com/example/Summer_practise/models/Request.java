package com.example.Summer_practise.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "request")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long request_id;

    private String contact_name;

    private String contact_phone;

    private String description; // описание

    private String executor; // исполнитель

    private String inn; //инн

    private String response; // ответ

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    private LocalDateTime closing_date;

    private LocalDateTime creation_date;
}
