package com.referAll.backend.services;

import com.referAll.backend.entities.dtos.ReferPostDto;

import java.util.List;

public interface ReferPostService {

    List<ReferPostDto> getAllReferPosts(String userId);

    List<ReferPostDto> getReferPostsByUserId(String userId);

    List<ReferPostDto> getAppliedReferPosts(String userId) throws Exception;

    String createReferPost(ReferPostDto newReferPostDto, String userId) throws Exception;

    String updateReferPost(ReferPostDto updatedReferPostDto, String userId, String referPostId) throws Exception;

    String deleteReferPost(String referPostId);

    String applyToReferPost(String referPostId, String userId);

    Boolean isUserAppliedToReferPost(String referPostId, String userId);

    String withdrawApplicationFromReferPost(String referPostId, String userId);
}
