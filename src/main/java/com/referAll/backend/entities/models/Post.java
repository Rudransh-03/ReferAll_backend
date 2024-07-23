package com.referAll.backend.entities.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Table(name = "POSTS")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class Post {
    @Id
    @Column(name = "post_id", unique = true, nullable = false)
    private String postId;

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

    @Column(name = "summary", length = 300)
    private String summary;

    @Column(name = "referred_status")
    private int referredStatus = 0;

    @Column(name = "referrer_id")
    private String referrerId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}


















