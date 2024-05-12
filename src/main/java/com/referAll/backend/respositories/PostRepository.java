package com.referAll.backend.respositories;

import com.referAll.backend.entities.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, String> {
}
