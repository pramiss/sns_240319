package com.sns.post;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sns.post.bo.PostBO;
import com.sns.post.entity.PostEntity;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/post")
@RestController
public class PostRestController {

	@Autowired
	private PostBO postBO;
	
	/**
	 * 글 생성 API
	 * @param content
	 * @param file
	 * @param session
	 * @return
	 */
	@PostMapping("/create")
	public Map<String, Object> create(
			@RequestParam(value = "content", required = false) String content,
			@RequestParam("file") MultipartFile file,
			HttpSession session) {
		// session에서 추가 정보 가져오기
		Integer userId = (Integer)session.getAttribute("userId");
		String loginId = (String)session.getAttribute("userLoginId");
		Map<String, Object> result = new HashMap<>();
		
		// 로그인 확인
		if (userId == null) {
			result.put("code", 403);
			result.put("error_message", "로그인을 해주세요.");
			return result;
		}
		
		// DB에 insert
		PostEntity post = postBO.addPost(content, file, userId, loginId);
		
		// AJAX return
		if (post != null) {
			result.put("code", 200);
			result.put("result", "성공");
		} else {
			result.put("code", 501);
			result.put("error_message", "파일 업로드에 실패했습니다.");
		}
		
		return result;
	}
	
	// 글 삭제 API
	@DeleteMapping("/delete")
	public Map<String, Object> delete(
			@RequestParam("postId") int postId,
			HttpSession session) {
		
		Map<String, Object> result = new HashMap<>();
		
		// 로그인 확인
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			result.put("code", 403);
			result.put("error_message", "로그인 해주세요.");
			return result;
		}
		
		// 1. 글(이미지, 댓글, 좋아요) 삭제
		postBO.deletePostEntityByIdAndUserId(postId, userId);
		
		// AJAX return
		result.put("code", 200);
		result.put("result", "성공");
		return result;
		
	} //-- 글 삭제 API
}
