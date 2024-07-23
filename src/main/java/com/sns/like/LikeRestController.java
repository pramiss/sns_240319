package com.sns.like;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sns.like.bo.LikeBO;

import jakarta.servlet.http.HttpSession;

@RestController
public class LikeRestController {
	
	@Autowired
	private LikeBO likeBO;
	
	// 새로운 방식으로 Request 요청
	// GET: "/like?postId=13 (기존 방식) => @RequestParam("postId") 사용함
	// GET: "/like/13" (새로운 방식) => @PathVariable
	@RequestMapping("/like/{postId}")
	public Map<String, Object> likeToggle(
			@PathVariable(name = "postId") int postId,
			HttpSession session) {
		Map<String, Object> result = new HashMap<>();
		
		// 로그인 확인
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			result.put("code", 403);
			result.put("error_message", "로그인이 필요합니다.");
			return result;
		}
		
		// likeToggole BO 요청
		likeBO.likeToggle(postId, userId);
		
		// AJAX return (성공만)
		result.put("code", 200);
		result.put("result", "성공");
		return result;
	}
}
