package com.sns.comment;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sns.comment.bo.CommentBO;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/comment")
@RestController
public class CommentRestController {
	
	@Autowired
	private CommentBO commentBO;
	
	/**
	 * 댓글 작성
	 * @param postId
	 * @param content
	 * @param session
	 * @return
	 */
	@PostMapping("/create")
	public Map<String, Object> create(
			@RequestParam("postId") int postId,
			@RequestParam("content") String content,
			HttpSession session) {
		// session에서 userId 받아오기
		Integer userId = (Integer)session.getAttribute("userId");
		
		Map<String, Object> result = new HashMap<>();
		
		if (userId == null) {
			// 로그인 실패
			result.put("code", 403);
			result.put("error_message", "로그인을 해주세요.");
			return result;
		}
		
		// DB insert - comment by userId, postId, content
		int rowCount = commentBO.addComment(userId, postId, content);
		
		// AJAX return
		if (rowCount > 0) {
			result.put("code", 200);
			result.put("result", "성공");
		} else {
			result.put("code", 501);
			result.put("error_message", "오류가 발생했습니다.");
		}
		
		return result;
	} //-- 댓글작성
	
	@DeleteMapping("delete")
	public Map<String, Object> delete(
			@RequestParam("commentId") int commentId,
			@RequestParam("userId") int userId,
			HttpSession session) {
		// 사용자 확인 (로그인 확인 겸용)
		Map<String, Object> result = new HashMap<>();
		
		if ((Integer)session.getAttribute("userId") != userId) {
			result.put("code", 403);
			result.put("error_message", "삭제에 실패했습니다.");
			return result;
		}
		
		// DB에서 제거
		int rowCount = commentBO.deleteCommentById(commentId);
		
		// AJAX return
		if (rowCount > 0) {
			result.put("code", 200);
			result.put("result", "성공");
		} else {
			result.put("code", 501);
			result.put("error_message", "알 수 없는 에러");
		}
		
		return result;
	} //-- 댓글삭제
}
