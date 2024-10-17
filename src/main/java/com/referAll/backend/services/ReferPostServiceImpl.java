package com.referAll.backend.services;

import com.referAll.backend.entities.dtos.ReferPostDto;
import com.referAll.backend.entities.models.ReferPost;
import com.referAll.backend.entities.models.User;
import com.referAll.backend.respositories.ReferPostRepository;
import com.referAll.backend.respositories.UserRepository;
import org.apache.commons.lang.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReferPostServiceImpl implements ReferPostService {

    @Autowired
    private ReferPostRepository referPostRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public static final int ID_LENGTH = 15;

    @Override
    public List<ReferPostDto> getAllReferPosts() {
        List<ReferPostDto> referPostDtoList = new ArrayList<>();

        for(ReferPost p : referPostRepository.findAll()){
            ReferPostDto referPostDto = modelMapper.map(p, ReferPostDto.class);
            referPostDtoList.add(referPostDto);
        }
        return referPostDtoList;
    }

    @Override
    public List<ReferPostDto> getReferPostsByUserId(String userId) {
        List<ReferPostDto> referPostDtoList = new ArrayList<>();

        for(ReferPost p : referPostRepository.findByUserId(userId)){
            ReferPostDto referPostDto = modelMapper.map(p, ReferPostDto.class);
            referPostDtoList.add(referPostDto);
        }

        referPostDtoList.sort((a, b)-> b.getCreationDate().compareTo(a.getCreationDate()));
        return referPostDtoList;
    }

    @Override
    public String createReferPost(ReferPostDto newReferPostDto, String userId) throws Exception {
        System.out.println("userId: "+userId);
        System.out.println("inside createReferPost impl");

        String id = generateUniqueId();
        while (referPostRepository.existsById(id)) {
            id = generateUniqueId();
        }

        System.out.println("id: "+id);

        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found with id: " + userId));

        System.out.println("user: "+user);

        LocalDateTime currentDateTime = LocalDateTime.now();
        // Define the formatter for yyyy-MM-dd format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // Format the current date and time to yyyy-MM-dd string
        String formattedDate = currentDateTime.format(formatter);
        // Print the formatted date
        System.out.println("Current Date in yyyy-MM-dd format: " + formattedDate);

        System.out.println("cn"+newReferPostDto.getCompanyName());

        newReferPostDto.setReferPostId(id);

        System.out.println("id"+newReferPostDto.getReferPostId());

        newReferPostDto.setCreator(user);

        System.out.println("user: "+newReferPostDto.getCreator());

        newReferPostDto.setCreationDate(formattedDate);

        System.out.println("date: "+newReferPostDto.getCreationDate());

        newReferPostDto.setCompanyName(user.getCurrentCompany().toLowerCase());

        System.out.println("cn: "+newReferPostDto.getCompanyName());

        System.out.println("newReferPostDto: "+newReferPostDto.getCreator().getFirstName());

        ReferPost referPost = modelMapper.map(newReferPostDto, ReferPost.class);
        System.out.println("referPost: "+referPost);
        referPostRepository.save(referPost);
        return "Post Created Successfully with postId: "+referPost.getReferPostId();
    }

    @Override
    public String updateReferPost(ReferPostDto updatedReferPostDto, String userId, String referPostId) throws Exception {
        ReferPost findPost = referPostRepository.findById(referPostId).orElseThrow(() -> new Exception("Post not found with id: " + referPostId));
        if(!findPost.getCreator().getUserId().equals(userId)) return "You are not authorized to change this post!";

        updatedReferPostDto.setReferPostId(referPostId);
        updatedReferPostDto.setCompanyName(updatedReferPostDto.getCompanyName().toLowerCase());

        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found with id: " + userId));
        updatedReferPostDto.setCreator(user);

        ReferPost referPost = modelMapper.map(updatedReferPostDto, ReferPost.class);
        referPostRepository.save(referPost);
        return "Post with postId: "+referPostId+" updated successfully";
    }

    @Override
    public String deleteReferPost(String referPostId) {
        Optional<ReferPost> referPostOptional = referPostRepository.findById(referPostId);
        if(referPostOptional.equals(Optional.empty())) return "Post with this postId does not exist!!!!";
        referPostRepository.deleteById(referPostId);
        return "Post Deleted Successfully";
    }

    @Override
    public String applyToReferPost(String referPostId, String userId) {
//        System.out.println("in applyToReferPost");
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<ReferPost> referPostOptional = referPostRepository.findById(referPostId);

        if(userOptional.isEmpty()) return "User with this id not found";
        if(referPostOptional.isEmpty()) return "Refer post with this id not found";
        User user = userOptional.get();
        ReferPost referPost= referPostOptional.get();

        List<User> applicantsList = referPost.getApplicants();

        if(applicantsList.contains(user)) return "You have already applied for the job!";

        applicantsList.add(user);
        referPost.setApplicants(applicantsList);
        referPostRepository.save(referPost);

        return "Applied successfully";
    }

    @Override
    public Boolean isUserAppliedToReferPost(String referPostId, String userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<ReferPost> referPostOptional = referPostRepository.findById(referPostId);

        if(userOptional.isEmpty()) return false;
        if(referPostOptional.isEmpty()) return false;
        User user = userOptional.get();
        ReferPost referPost= referPostOptional.get();

        List<User> applicantsList = referPost.getApplicants();
        return applicantsList.contains(user);
    }

    @Override
    public String withdrawApplicationFromReferPost(String referPostId, String userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<ReferPost> referPostOptional = referPostRepository.findById(referPostId);

        if(userOptional.isEmpty()) return "User with this id not found";
        if(referPostOptional.isEmpty()) return "Refer post with this id not found";
        User user = userOptional.get();
        ReferPost referPost= referPostOptional.get();

        List<User> applicantsList = referPost.getApplicants();

        if(!applicantsList.contains(user)) return "Application doesn't exist";

        applicantsList.remove(user);
        referPost.setApplicants(applicantsList);
        referPostRepository.save(referPost);

        return "Application withdrawn successfully";
    }

    public String generateUniqueId() {
        return RandomStringUtils.randomAlphanumeric(ID_LENGTH);
    }
}
