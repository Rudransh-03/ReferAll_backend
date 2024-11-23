package com.referAll.backend.entities.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Table(name = "APPLICANTS")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class Applicant{

    @Id
    @Column(name = "applicant_id", unique = true, nullable = false)
    private String applicantId;

    @Column(name = "applicant_status")
    private String applicantStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "refer_post_id")
    private ReferPost referPost;

    @Column(name = "creation_date")
    private String creationDate;
}
