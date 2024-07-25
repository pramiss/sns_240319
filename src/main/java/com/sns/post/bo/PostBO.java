package com.sns.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sns.comment.bo.CommentBO;
import com.sns.common.FileManagerService;
import com.sns.like.bo.LikeBO;
import com.sns.post.entity.PostEntity;
import com.sns.post.repository.PostRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PostBO {

	@Autowired
	private CommentBO commentBO;
	
	@Autowired
	private LikeBO likeBO;
	
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
	
	// 글 삭제
	// input: (postId, userId), output: void
	@Transactional // 트랜잭션으로 작동. 에러가 나면 롤백
	public void deletePostEntityByIdAndUserId(int postId, int userId) {
		
		// 1. 삭제할 글 가져오기(validation 기능)
		PostEntity post = postRepository.findByIdAndUserId(postId, userId);
		if (post == null) {
			log.info("[글 삭제] post is null. postId:{}, userId:{}", postId, userId);
			return; // 삭제할 글이 없음
		}
		
		// 2. 글 삭제
		postRepository.deleteById(postId);
		
		// 3. 이미지 삭제 : -1(이미지 삭제 미완료)
		fileManagerService.deleteFile(post.getImagePath());
		
		// 4. 댓글 삭제
		commentBO.deleteCommentByPostId(postId);
		
		// 5. 좋아요 삭제
		likeBO.deleteLikeByPostId(postId);
		
		return;
	} //-- 글 삭제
}
