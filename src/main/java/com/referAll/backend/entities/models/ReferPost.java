package com.referAll.backend.entities.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Table(name = "REFER_POSTS")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class ReferPost {
    @Id
    @Column(name = "refer_post_id", unique = true, nullable = false)
    private String referPostId;

    @Column(name = "creation_date")
    private String creationDate; //(YYYY-MM-DD)

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "job_id")
    private String jobId;

    @Column(name = "job_url")
    private String jobUrl;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "job_description")
    private String jobDescription;

    @Column(name = "yoe_required")
    private String yoeRequired;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User creator;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "refer_post_applicants",
            joinColumns = @JoinColumn(name = "refer_post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> applicants;
}
