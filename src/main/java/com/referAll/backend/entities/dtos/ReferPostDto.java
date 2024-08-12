package com.referAll.backend.entities.dtos;

import com.referAll.backend.entities.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReferPostDto {
    private String referPostId;

    private String creationDate; //(YYYY-MM-DD)

    private String companyName;

    private String jobId;

    private String jobUrl;

    private String jobTitle;

    private String jobDescription;

    private String yoeRequired;

    private User creator;

    List<User> applicants;
}
