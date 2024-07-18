package com.sns.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sns.common.FileManagerService;
import com.sns.post.entity.PostEntity;
import com.sns.post.repository.PostRepository;

@Service
public class PostBO {

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private FileManagerService fileManagerService;
	
	// post 전체 가져오기
	// input: x, output: List<PostEntity>
	public List<PostEntity> getPostEntityList() {
		return postRepository.findByOrderByIdDesc();
	} //-- post 전체 가져오기
	
	// post create
	// input: content, file, userId, loginId
	// output: postEntity
	public PostEntity addPost(String content, MultipartFile file, int userId, String loginId) {
		// 1. file을 저장하고 imagePath를 받아오기 (이미지는 필수)
		String imagePath = fileManagerService.uploadFile(file, loginId);
		
		if (imagePath == null) {
			// 파일 업로드 실패
			return null;
		}
		
		// 2. post Entity 만들기
		PostEntity post = PostEntity.builder()
							.userId(userId)
							.imagePath(imagePath)
							.content(content)
							.build();
		
		// 3. 저장 및 리턴
		return postRepository.save(post);
	} //-- post create
}
