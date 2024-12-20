package com.referAll.backend.entities.dtos;

import com.referAll.backend.entities.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private String postId;

    private String creationDate;

    private String companyName;

    private String jobId;

    private String jobUrl;

    private String jobTitle;

    private String summary;

    private int referredStatus = 0;

    private String referrerId;

    private User user;

    private User referrer;
}
