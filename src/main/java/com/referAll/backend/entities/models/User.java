package com.referAll.backend.entities.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Table(name = "USERS")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class User {

    @Id
    @Column(name = "user_id", unique = true, nullable = false)
    private String userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "password")
    private String password;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "current_title")
    private String currentTitle;

    @Column(name = "current_company")
    private String currentCompany;

    @Column(name = "resume_url")
    private String resumeUrl;

    @Column(name = "linkedIn_url")
    private String linkedInUrl;

    @Column(name = "bio", length = 300)
    private String bio;

    @Column(name = "points")
    private Long points=0L;

    @Transient
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> postsList;
}
