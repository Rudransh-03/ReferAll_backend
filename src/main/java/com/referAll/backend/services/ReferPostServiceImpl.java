package com.referAll.backend.services;

import com.referAll.backend.entities.dtos.ReferPostDto;
import com.referAll.backend.entities.models.Applicant;
import com.referAll.backend.entities.models.ReferPost;
import com.referAll.backend.entities.models.User;
import com.referAll.backend.respositories.ApplicantRepository;
import com.referAll.backend.respositories.ReferPostRepository;
import com.referAll.backend.respositories.UserRepository;
import org.apache.commons.lang.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Ref;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ReferPostServiceImpl implements ReferPostService {

    @Autowired
    private ReferPostRepository referPostRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private ModelMapper modelMapper;

    public static final int ID_LENGTH = 15;

    @Override
    public List<ReferPostDto> getAllReferPosts(String userId) {
        System.out.println("inside getAllReferPosts fxn");
        List<ReferPostDto> referPostDtoList = new ArrayList<>();
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()) return new ArrayList<>();
        System.out.println("line 45");

        User user = optionalUser.get();

        List<Applicant> referPostsApplicantList = user.getReferPostsApplicantList();
        System.out.println("referPostsApplicantList size: "+referPostsApplicantList.size());
//        for(Applicant a : referPostsApplicantList) System.out.println(a.toString());

        boolean flag = false;
        for(ReferPost p : referPostRepository.findAll()){
            flag = false;
            System.out.println(p.toString());
            System.out.println("line 55");
            for(Applicant a : referPostsApplicantList){
                if(a.getReferPost().equals(p)){
                    flag = true;
                    break;
                }
            }
            if(flag) continue;
            ReferPostDto referPostDto = modelMapper.map(p, ReferPostDto.class);
            System.out.println("getAllReferPosts "+user.getCurrentCompany()+" "+referPostDto.getCompanyName());
            if(!referPostDto.getCompanyName().equalsIgnoreCase(user.getCurrentCompany())) referPostDtoList.add(referPostDto);
        }
        referPostDtoList.sort((a, b)-> b.getCreationDate().compareTo(a.getCreationDate()));
        return referPostDtoList;
    }

    @Override
    public List<ReferPostDto> getReferPostsByUserId(String userId) {
        List<ReferPostDto> referPostDtoList = new ArrayList<>();

        for(ReferPost p : referPostRepository.findByUserId(userId)){
            ReferPostDto referPostDto = modelMapper.map(p, ReferPostDto.class);
            List<Applicant> applicantList = referPostDto.getApplicants();
            applicantList.sort((a, b)-> b.getCreationDate().compareTo(a.getCreationDate()));
            for(Applicant a : applicantList) System.out.println(a.getUser().getFirstName()+" "+a.getCreationDate());
            referPostDtoList.add(referPostDto);
        }


        referPostDtoList.sort((a, b)-> b.getCreationDate().compareTo(a.getCreationDate()));
        return referPostDtoList;
    }

    @Override
    public List<ReferPostDto> getAppliedReferPosts(String userId) throws Exception {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()) return new ArrayList<>();
        User user = userOptional.get();

        System.out.println(user.getFirstName());

        List<ReferPostDto> referPostDtoList = new ArrayList<>();
        List<Applicant> referPostsApplicantList = user.getReferPostsApplicantList();

        System.out.println("referPostsApplicantList size: " + referPostsApplicantList.size());
        System.out.println("referPostsApplicantList company name: " + referPostsApplicantList.get(0).getReferPost().getCompanyName());

        for(Applicant referPostApplicant: referPostsApplicantList){
            ReferPost referPost = referPostApplicant.getReferPost();
            System.out.println("line number 90: "+referPost.getCompanyName());
            System.out.println("91 "+referPost.toString());
            ReferPostDto referPostDto = modelMapper.map(referPost, ReferPostDto.class);
            System.out.println(referPostDto.getCompanyName());
            referPostDtoList.add(referPostDto);
            System.out.println("line 94 "+referPostDtoList.contains(referPostDto));
        }

        System.out.println("referPostDtoList size: "+referPostDtoList.size());

        if(referPostDtoList.isEmpty()){
            throw new Exception("Khaali hai!!!!!");
        }

        referPostDtoList.sort((a, b)-> b.getCreationDate().compareTo(a.getCreationDate()));
        System.out.println(referPostDtoList.toString());
        return referPostDtoList;
    }

    @Override
    public String createReferPost(ReferPostDto newReferPostDto, String userId) throws Exception {
        String id = generateUniqueId();
        while (referPostRepository.existsById(id)) {
            id = generateUniqueId();
        }

        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found with id: " + userId));
        LocalDateTime currentDateTime = LocalDateTime.now();
        // Define the formatter for yyyy-MM-dd format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // Format the current date and time to yyyy-MM-dd string
        String formattedDate = currentDateTime.format(formatter);
        newReferPostDto.setReferPostId(id);
        newReferPostDto.setCreator(user);
        newReferPostDto.setCreationDate(formattedDate);
        newReferPostDto.setCompanyName(user.getCurrentCompany().toLowerCase());
        ReferPost referPost = modelMapper.map(newReferPostDto, ReferPost.class);
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
        System.out.println("Inside deleteReferPost fxn");
        Optional<ReferPost> referPostOptional = referPostRepository.findById(referPostId);
        if(referPostOptional.equals(Optional.empty())) return "Post with this postId does not exist!!!!";
        System.out.println("line 147");
        if(!applicantRepository.findByReferPostId(referPostId).isEmpty()){
            applicantRepository.deleteByReferPost(referPostId);
        }
        System.out.println("line 149");
        referPostRepository.deleteById(referPostId);
        System.out.println("Post deleted");
        return "Post Deleted Successfully";
    }

    @Override
    public String applyToReferPost(String referPostId, String userId) {
        System.out.println("in applyToReferPost");
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<ReferPost> referPostOptional = referPostRepository.findById(referPostId);

        if(userOptional.isEmpty()) return "User with this id not found";
        if(referPostOptional.isEmpty()) return "Refer post with this id not found";
        User user = userOptional.get();
        ReferPost referPost= referPostOptional.get();

        List<Applicant> applicantsList = referPost.getApplicants();

        for(Applicant applicant : applicantsList){
            if(applicant.getUser().equals(user)) return "You have already applied for the job!";
        }

        Applicant applicant = createNewApplicant(user, referPost, "");

        applicantsList.add(applicant);
        applicantsList.sort((a, b)-> b.getCreationDate().compareTo(a.getCreationDate()));
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

        List<Applicant> applicantsList = referPost.getApplicants();
        for(Applicant a:applicantsList){
            if(a.getUser().equals(user)) return true;
        }
        return false;
    }

    @Override
    public String withdrawApplicationFromReferPost(String referPostId, String userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<ReferPost> referPostOptional = referPostRepository.findById(referPostId);

        if(userOptional.isEmpty()) return "User with this id not found";
        if(referPostOptional.isEmpty()) return "Refer post with this id not found";
        User user = userOptional.get();
        ReferPost referPost= referPostOptional.get();

        List<Applicant> applicantsList = referPost.getApplicants();

        for(Applicant a : applicantsList){
            if(a.getUser().equals(user)){
                applicantsList.remove(a);
                referPost.setApplicants(applicantsList);
                referPostRepository.save(referPost);
                applicantRepository.deleteById(a.getApplicantId());
                return "Application withdrawn successfully";
            }
        }

        return "Application doesn't exist";
    }

    private String generateUniqueId() {
        return RandomStringUtils.randomAlphanumeric(ID_LENGTH);
    }

    private Applicant createNewApplicant(User user, ReferPost referPost, String status){
        String id = generateUniqueId();
        while (applicantRepository.existsById(id)) {
            id = generateUniqueId();
        }

        LocalDateTime currentDateTime = LocalDateTime.now();
// Define the formatter for yyyy-MM-dd and second-level precision
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
// Format the current date and time to yyyy-MM-dd HH:mm:ss string
        String formattedDate = currentDateTime.format(formatter);

        System.out.println("formatted Date: "+formattedDate);

        Applicant applicant = new Applicant(id, status, user, referPost, formattedDate);
        applicantRepository.save(applicant);
        System.out.println("New Applicant created: id: "+id+" first name: "+user.getFirstName() +" referPostCompany: "+referPost.getCompanyName());
        return applicant;
    }
}
