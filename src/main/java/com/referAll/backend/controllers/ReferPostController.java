package com.referAll.backend.controllers;

import com.referAll.backend.entities.dtos.ReferPostDto;
import com.referAll.backend.services.ReferPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReferPostController {

    @Autowired
    private ReferPostService referPostService;

    @GetMapping("/referPosts/getAllReferPosts")
    public ResponseEntity<List<ReferPostDto>> getAllReferPosts(){
        List<ReferPostDto> referPostDtosList = referPostService.getAllReferPosts();
        return ResponseEntity.ok(referPostDtosList);
    }


    @GetMapping("referPosts/getReferPostsByUserId/{userId}")
    public ResponseEntity<List<ReferPostDto>> getReferPostsByUserId(@PathVariable String userId){
        System.out.println("inside getReferPostsByUserId fxn");
        List<ReferPostDto> referPostDtosList = referPostService.getReferPostsByUserId(userId);
        return ResponseEntity.ok(referPostDtosList);
    }

    @PostMapping("referPosts/createReferPost/{userId}")
    public ResponseEntity<String> createReferPost(@RequestBody ReferPostDto newReferPostDto, @PathVariable String userId) throws Exception {
        System.out.println("inside createReferPost function");
        String response = referPostService.createReferPost(newReferPostDto, userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/referPosts/updateReferPost/{userId}/{referPostId}")
    public ResponseEntity<String> updateReferPost(@RequestBody ReferPostDto updatedReferPostDto, @PathVariable String userId, @PathVariable String referPostId) throws Exception {
        String response = referPostService.updateReferPost(updatedReferPostDto, userId, referPostId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("referPosts/deleteReferPost/{referPostId}")
    public ResponseEntity<String> deleteReferPost(@PathVariable String referPostId){
        String response = referPostService.deleteReferPost(referPostId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("referPosts/applyToReferPost/{referPostId}/{userId}")
    public ResponseEntity<String> applyToReferPost(@PathVariable String referPostId, @PathVariable String userId){
        String response = referPostService.applyToReferPost(referPostId, userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("referPosts/isAppliedToReferPost/{referPostId}/{userId}")
    public ResponseEntity<Boolean> isUserAppliedToReferPost(@PathVariable String referPostId, @PathVariable String userId){
        Boolean response = referPostService.isUserAppliedToReferPost(referPostId, userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("referPosts/withdrawApplicationFromReferPost/{referPostId}/{userId}")
    public ResponseEntity<String> withdrawApplicationFromReferPost(@PathVariable String referPostId, @PathVariable String userId){
        String response = referPostService.withdrawApplicationFromReferPost(referPostId, userId);
        return ResponseEntity.ok(response);
    }
}
