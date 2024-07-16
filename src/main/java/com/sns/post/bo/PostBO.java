package com.sns.post.bo;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sns.post.entity.PostEntity;
import com.sns.post.repository.PostRepository;

@Service
public class PostBO {
	
	private PostRepository postRepository;
	
	public PostBO(PostRepository postRepository) {
		this.postRepository = postRepository;
	}
	
	// post 전체 가져오기
	// input: x, output: List<PostEntity>
	public List<PostEntity> getPostEntityList() {
		return postRepository.findByOrderByIdDesc();
	}
}
