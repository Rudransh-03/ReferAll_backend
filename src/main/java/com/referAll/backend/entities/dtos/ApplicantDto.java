package com.referAll.backend.entities.dtos;

import com.referAll.backend.entities.models.ReferPost;
import com.referAll.backend.entities.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicantDto {

    private String applicantId;

    private String applicantStatus;

    private User user;

    private ReferPost referPost;

    private String creationDate;
}
