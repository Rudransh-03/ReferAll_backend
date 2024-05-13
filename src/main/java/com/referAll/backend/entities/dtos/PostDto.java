package com.referAll.backend.entities.dtos;

import com.referAll.backend.entities.models.User;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    private String companyName;

    private String jobId;

    private String jobUrl;

    private String jobTitle;

    private String summary;

    private int referredStatus = 0;

    private User user;
}
