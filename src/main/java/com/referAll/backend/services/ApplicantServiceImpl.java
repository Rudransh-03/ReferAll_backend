package com.referAll.backend.services;

import com.referAll.backend.respositories.ApplicantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class ApplicantServiceImpl implements ApplicantService{

    @Autowired
    public ApplicantRepository applicantRepository;

    @Override
    public String updateApplicantsStatus(HashMap<String, String> updatedStatusList) {

        try{
            for(String key: updatedStatusList.keySet()){
                String applicantId = key;
                String updatedStatus = updatedStatusList.get(key);

                applicantRepository.updateApplicantStatus(applicantId, updatedStatus);
            }

            return "Statuses updated successfully";
        }

        catch (Exception e){
            System.out.println(e.getMessage());
            return e.getMessage();
        }

    }
}
