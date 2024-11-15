package com.referAll.backend.controllers;

import com.referAll.backend.entities.dtos.PostDto;
import com.referAll.backend.services.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
public class ApplicantController {

    @Autowired
    public ApplicantService applicantService;

    @PostMapping("/applicant/updateApplicantsStatus")
    public ResponseEntity<String> updateApplicantsStatus(@RequestBody HashMap<String, String> updatedStatusList){
        System.out.println("hi");
        for(String key: updatedStatusList.keySet()) System.out.println(key + updatedStatusList.get(key));

        String response = applicantService.updateApplicantsStatus(updatedStatusList);

        return ResponseEntity.ok("");
    }
}
