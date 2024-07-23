package com.referAll.backend.services;

import com.referAll.backend.entities.dtos.PostDto;

import java.util.List;

public interface PostService {

    List<PostDto> getPosts();

    int getTotalPostsCountByCompany(String companyName);

    List<PostDto> getPostsByUser(String userId);

    List<PostDto> getPostsByCompany(String companyName);

    List<PostDto> getPaginatedPostsByCompany(int pageNumber, String companyName);

    PostDto getPostsByPostId(String postId);

    List<PostDto> getFilteredPostsByReferredStatusAndSearchTerm(String referredStatus, String searchTerm, String companyName);

    List<PostDto> getFilteredPostsByReferredStatus(String referredStatus, String companyName);

    String changeIsReferredToInProgress(String postId, String userId) throws Exception;

    String changeIsReferredToReferred(String postId, String userId) throws Exception;

    String addPost(PostDto newPostDto, String userId) throws Exception;

    String updatePost(PostDto postDto, String userId, String postId) throws Exception;

    String deletePost(String postId) throws Exception;
}
