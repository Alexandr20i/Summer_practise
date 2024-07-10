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

//    @Column(name = "contact_name", nullable = false)
    private String contact_name;

//    @Column(name = "contact_phone", nullable = false)
    private String contact_phone;

//    @Column(name = "description")
    private String description; // описание

//    @Column(name = "executor")
    private String executor; // исполнитель

//    @Column(name = "inn", nullable = false)
    private String inn; //инн

//    @Column(name = "response")
    private String response; // ответ

//    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RequestStatus status;

//    @Column(name = "closing_date", nullable = true)
    private LocalDateTime closing_date;

//    @Column(name = "creation_date", nullable = false, updatable = false)
    private LocalDateTime creation_date;
}
