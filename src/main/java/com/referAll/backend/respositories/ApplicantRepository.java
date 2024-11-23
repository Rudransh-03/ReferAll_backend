package com.referAll.backend.respositories;

import com.referAll.backend.entities.models.Applicant;
import com.referAll.backend.entities.models.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicantRepository extends CrudRepository<Applicant, String> {
    @Query(value = "SELECT * FROM APPLICANTS where refer_post_id = ?1", nativeQuery = true)
    public List<Applicant> findByReferPostId(String referPostId);

    @Query(value = "DELETE FROM APPLICANTS where refer_post_id = ?1", nativeQuery = true)
    public void deleteByReferPost(String referPostId);

    @Query(value = "UPDATE APPLICANTS set applicant_status = ?2 WHERE applicant_id=?1", nativeQuery = true)
    public void updateApplicantStatus(String applicantId, String updatedStatus);
}
