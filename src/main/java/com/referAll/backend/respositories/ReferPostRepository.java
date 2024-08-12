package com.referAll.backend.respositories;

import com.referAll.backend.entities.models.Post;
import com.referAll.backend.entities.models.ReferPost;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReferPostRepository extends CrudRepository<ReferPost, String> {

    @Query(value = "SELECT * FROM REFER_POSTS where user_id = ?1", nativeQuery = true)
    public List<ReferPost> findByUserId(String userId);
}
