package com.referAll.backend.respositories;

import com.referAll.backend.entities.dtos.PostDto;
import com.referAll.backend.entities.models.Post;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, String> {

    @Query(value = "SELECT COUNT(*) FROM POSTS WHERE LOWER(company_name) = LOWER(?1)", nativeQuery = true)
    public int getTotalPostsCountByCompany(String companyName);

    @Query(value = "SELECT * FROM POSTS where referrer_id = ?1", nativeQuery = true)
    public List<Post> findPostsByReferrerId(String userId);

    @Query(value = "SELECT * FROM POSTS where user_id = ?1", nativeQuery = true)
    public List<Post> findByUserID(String userId);

    @Query(value = "SELECT * FROM POSTS where company_name = ?1", nativeQuery = true)
    public List<Post> findByCompanyName(String companyName);

    @Query(value = "SELECT * FROM POSTS WHERE LOWER(company_name) = LOWER(?1) LIMIT 5 OFFSET ?2", nativeQuery = true)
    public List<Post> findPaginatedPostsByCompanyName(String companyName, int startingIndex);

    @Query(value = "SELECT * FROM POSTS where post_id = ?1", nativeQuery = true)
    public Post findByPostId(String postId);

    @Query(value = "SELECT * FROM POSTS WHERE referred_status = ?1 AND (LOWER(job_id) LIKE LOWER(CONCAT('%', ?2, '%')) OR LOWER(job_title) LIKE LOWER(CONCAT('%', ?2, '%'))) AND LOWER(company_name) = LOWER(?3)", nativeQuery = true)
    public List<Post> getFilteredPostsByReferredStatusAndSearchTerm(int referredStatus, String searchTerm, String companyName);

    @Query(value = "SELECT * FROM POSTS WHERE referred_status = ?1 AND LOWER(company_name) = LOWER(?2)", nativeQuery = true)
    public List<Post> getFilteredPostsByReferredStatus(int referredStatus, String companyName);

    @Modifying
    @Query(value = "DELETE FROM POSTS WHERE creation_date <= ?1 AND referred_status = 0", nativeQuery = true)
    public void deleteByCreatedAtBefore(String thresholdDate);

}
