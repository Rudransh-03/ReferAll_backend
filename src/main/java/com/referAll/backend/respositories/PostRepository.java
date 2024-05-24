package com.referAll.backend.respositories;

import com.referAll.backend.entities.models.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, String> {

    @Query(value = "SELECT * FROM POSTS where user_id = ?1", nativeQuery = true)
    public List<Post> findByUserID(String userId);

    @Query(value = "SELECT * FROM POSTS where company_name = ?1", nativeQuery = true)
    public List<Post> findByCompanyName(String companyName);

    @Query(value = "SELECT * FROM POSTS where post_id = ?1", nativeQuery = true)
    public Post findByPostId(String postId);
}
