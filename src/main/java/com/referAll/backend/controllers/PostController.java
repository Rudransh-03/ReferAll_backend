package com.referAll.backend.controllers;

import com.referAll.backend.entities.dtos.PostDto;
import com.referAll.backend.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/posts/getAllPosts")
    public ResponseEntity<List<PostDto>> getPosts(){
        List<PostDto> postDtoList = postService.getPosts();
        return ResponseEntity.ok(postDtoList);
    }

    @GetMapping("/posts/getTotalPostsCountByCompany/{companyName}")
    public ResponseEntity<Integer> getTotalPostsCountByCompany(@PathVariable String companyName){
        int count = postService.getTotalPostsCountByCompany(companyName);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/posts/getPostsByUser/{userId}")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable String userId){
        List<PostDto> postDtoList = postService.getPostsByUser(userId);
        return ResponseEntity.ok(postDtoList);
    }

    @GetMapping("/posts/getPostsByPostId/{postId}")
    public ResponseEntity<PostDto> getPostsByPostId(@PathVariable String postId){
        PostDto postDto = postService.getPostsByPostId(postId);
        return ResponseEntity.ok(postDto);
    }

    @GetMapping("/posts/getPostsByCompany/{companyName}")
    public ResponseEntity<List<PostDto>> getPostsByCompany(@PathVariable String companyName){
        List<PostDto> postDtoList = postService.getPostsByCompany(companyName);
        return ResponseEntity.ok(postDtoList);
    }

    @GetMapping("/posts/getPaginatedPostsByCompany/{companyName}")
    public ResponseEntity<List<PostDto>> getPaginatedPostsByCompany(@RequestParam("pageNumber") int pageNumber,@PathVariable String companyName){
        List<PostDto> postDtoList = postService.getPaginatedPostsByCompany(pageNumber, companyName);
        return ResponseEntity.ok(postDtoList);
    }

    @GetMapping("/posts/getFilteredPostsByReferredStatus/{companyName}")
    public ResponseEntity<?> getFilteredPostsByReferredStatus(@RequestParam("referredStatus") String referredStatus, @PathVariable String companyName){
        System.out.println(referredStatus+" "+companyName);
        System.out.println("---------------");
        List<PostDto> filteredPostsByReferredStatus = postService.getFilteredPostsByReferredStatus(referredStatus, companyName);
        if(filteredPostsByReferredStatus.isEmpty()){
            return ResponseEntity.ok("No posts found");
        }
        return ResponseEntity.ok(filteredPostsByReferredStatus);
    }

    @GetMapping("/posts/getFilteredPostsByBothReferredStatusAndSearchTerm/{companyName}")
    public ResponseEntity<?> getFilteredPostsByReferredStatusAndSearchTerm(@RequestParam("referredStatus") String referredStatus, @RequestParam("searchTerm") String searchTerm, @PathVariable String companyName){
        System.out.println(referredStatus + " "+searchTerm+" "+companyName);
        List<PostDto> filteredPostsByReferredStatusAndSearchTerm = postService.getFilteredPostsByReferredStatusAndSearchTerm(referredStatus, searchTerm, companyName);
        if(filteredPostsByReferredStatusAndSearchTerm.isEmpty()){
            return ResponseEntity.ok("No posts found");
        }
        System.out.println(filteredPostsByReferredStatusAndSearchTerm.size());
        return ResponseEntity.ok(filteredPostsByReferredStatusAndSearchTerm);
    }


    @GetMapping("/changeIsReferredToInProgress/{postId}/{userId}")
    public ResponseEntity<String> changeIsReferredToInProgress(@PathVariable String postId, @PathVariable String userId) throws Exception {
        System.out.println("in changeIsReferredToInProgress method");
        String response = postService.changeIsReferredToInProgress(postId, userId);
        System.out.println(response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/changeIsReferredToReferred/{postId}/{userId}")
    public ResponseEntity<String> changeIsReferredToReferred(@PathVariable String postId, @PathVariable String userId) throws Exception {
        String response = postService.changeIsReferredToReferred(postId, userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/posts/addPost/{userId}")
    public ResponseEntity<String> addPost(@RequestBody PostDto newPostDto, @PathVariable String userId) throws Exception {
        System.out.println("UserId: "+userId);
        String response = postService.addPost(newPostDto, userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/posts/updatePost/{userId}/{postId}")
    public ResponseEntity<String> updatePost(@RequestBody PostDto updatedPostDto, @PathVariable String userId, @PathVariable String postId) throws Exception {
        String response = postService.updatePost(updatedPostDto, userId, postId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/posts/deletePost/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable String postId) throws Exception {
        String response = postService.deletePost(postId);
        return ResponseEntity.ok(response);
    }
}
