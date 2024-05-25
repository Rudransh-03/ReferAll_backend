package com.referAll.backend.services;

import com.referAll.backend.entities.dtos.PostDto;
import com.referAll.backend.entities.models.Post;
import com.referAll.backend.entities.models.User;
import com.referAll.backend.respositories.PostRepository;
import com.referAll.backend.respositories.UserRepository;
import org.apache.commons.lang.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public static final int ID_LENGTH = 15;

    @Override
    public List<PostDto> getPosts() {
        List<PostDto> postDtoList = new ArrayList<>();

        for(Post p : postRepository.findAll()){
            PostDto postDto = modelMapper.map(p, PostDto.class);
            postDtoList.add(postDto);
        }
        return postDtoList;
    }

    @Override
    public List<PostDto> getPostsByUser(String userId) {
        List<PostDto> postDtoList = new ArrayList<>();
        for(Post p : postRepository.findByUserID(userId)){
            PostDto postDto = modelMapper.map(p, PostDto.class);
            postDtoList.add(postDto);
        }
        return postDtoList;
    }

    @Override
    public List<PostDto> getPostsByCompany(String companyName) {
//        System.out.println(companyName);
        List<PostDto> postDtoList = new ArrayList<>();
        for(Post p : postRepository.findByCompanyName(companyName.toLowerCase())){
//            System.out.println(p.getUser().getFirstName());
            PostDto postDto = modelMapper.map(p, PostDto.class);
            postDtoList.add(postDto);
        }

        postDtoList.sort((a, b) -> {
            Long pointsA = a.getUser().getPoints();
            Long pointsB = b.getUser().getPoints();

            if(a.getReferredStatus() == b.getReferredStatus()) return pointsB.compareTo(pointsA);
            return (a.getReferredStatus() - b.getReferredStatus());
        });

        return postDtoList;
    }

    @Override
    public PostDto getPostsByPostId(String postId) {
        Post post = postRepository.findByPostId(postId);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> getFilteredPostsByReferredStatusAndSearchTerm(String referredStatus, String searchTerm, String companyName) {

//        if(referredStatus.equalsIgnoreCase("none")) return getPostsByCompany(companyName);

        int referredStatusInt = 0;
        if(referredStatus.equalsIgnoreCase("Unreferred")) referredStatusInt = 0;
        else if(referredStatus.equalsIgnoreCase("in progress")) referredStatusInt = 1;
        else referredStatusInt = 2;

        System.out.println("referredStatus "+referredStatusInt);

        List<PostDto> filteredPostsDtoByReferredStatusAndSearchTerm = new ArrayList<>();

        for(Post p : postRepository.getFilteredPostsByReferredStatusAndSearchTerm(referredStatusInt, searchTerm, companyName.toLowerCase())){
            PostDto postDto = modelMapper.map(p, PostDto.class);
            filteredPostsDtoByReferredStatusAndSearchTerm.add(postDto);
        }

        return filteredPostsDtoByReferredStatusAndSearchTerm;
    }

    @Override
    public List<PostDto> getFilteredPostsByReferredStatus(String referredStatus, String companyName) {
        try {
            System.out.println("referredStatus: " + referredStatus);
            System.out.println("companyName: " + companyName);

            if (referredStatus.equalsIgnoreCase("none")) {
                System.out.println("idhr waala none");
                return getPostsByCompany(companyName);
            }

            int referredStatusInt = 0;
            if (referredStatus.equalsIgnoreCase("Unreferred")) referredStatusInt = 0;
            else if (referredStatus.equalsIgnoreCase("in progress")) referredStatusInt = 1;
            else referredStatusInt = 2;

            System.out.println("referredStatus (int): " + referredStatusInt);

            List<PostDto> filteredPostsDtoByReferredStatus = new ArrayList<>();

            List<Post> filteredPosts = postRepository.getFilteredPostsByReferredStatus(referredStatusInt, companyName);
            System.out.println("Number of filtered posts: " + filteredPosts.size());

            for (Post p : filteredPosts) {
                PostDto postDto = modelMapper.map(p, PostDto.class);
                filteredPostsDtoByReferredStatus.add(postDto);
            }

            return filteredPostsDtoByReferredStatus;
        } catch (Exception ex) {
            System.err.println("Error in getFilteredPostsByReferredStatus: " + ex.getMessage());
            ex.printStackTrace();
            return Collections.emptyList(); // Or handle the exception according to your application logic
        }
    }

    @Override
    public String changeIsReferredToInProgress(String postId) throws Exception {
        Post post = postRepository.findById(postId).orElseThrow(() -> new Exception("Post not found with id: " + postId));
        post.setReferredStatus(1);
        postRepository.save(post);
        return "Status of the post with postId- "+postId+" changed to in-progress!";
    }

    @Override
    public String changeIsReferredToReferred(String postId) throws Exception{
        Post post = postRepository.findById(postId).orElseThrow(() -> new Exception("Post not found with id: " + postId));
        post.setReferredStatus(2);
        postRepository.save(post);
        return "Status of the post with postId- "+postId+" changed to referred!";
    }

    @Override
    public String addPost(PostDto newPostDto, String userId) throws Exception {
        String id = generateUniqueId();
        while (postRepository.existsById(id)) {
            id = generateUniqueId();
        }
        newPostDto.setCompanyName(newPostDto.getCompanyName().toLowerCase());
        newPostDto.setPostId(id);
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found with id: " + userId));
        newPostDto.setUser(user);
        Post newPost = modelMapper.map(newPostDto, Post.class);
        postRepository.save(newPost);
        return "Post Created Successfully with postId: "+newPost.getPostId();
    }

    @Override
    public String updatePost(PostDto updatedPostDto, String userId, String postId) throws Exception{

        Post findPost = postRepository.findById(postId).orElseThrow(() -> new Exception("Post not found with id: " + postId));
        if(!findPost.getUser().getUserId().equals(userId)) return "You are not authorized to change this post!";

        updatedPostDto.setPostId(postId);
        updatedPostDto.setCompanyName(updatedPostDto.getCompanyName().toLowerCase());

        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found with id: " + userId));
        updatedPostDto.setUser(user);

        Post post = modelMapper.map(updatedPostDto, Post.class);
        postRepository.save(post);
        return "Post with postId: "+postId+" updated successfully";
    }

    @Override
    public String deletePost(String postId) throws Exception{
        Optional<Post> post = postRepository.findById(postId);
        if(post.equals(Optional.empty())) return "Post with this postId does not exist!!!!";
        postRepository.deleteById(postId);
        return "Post Deleted Successfully";
    }

    public String generateUniqueId() {
        return RandomStringUtils.randomAlphanumeric(ID_LENGTH);
    }
}
