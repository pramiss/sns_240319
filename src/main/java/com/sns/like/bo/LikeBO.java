package com.sns.like.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.like.domain.Like;
import com.sns.like.mapper.LikeMapper;

@Service
public class LikeBO {
	
	@Autowired
	private LikeMapper likeMapper;

	// 좋아요 채울지 여부
	// input: (postId(int:필수), sessionUserId(Integer:로그인/비로그인)
	// output: boolean(채울지 여부)
	public boolean likedByPostIdAndUserId(int postId, Integer userId) {
		// 비로그인
		if (userId == null) return false;
		
		// 로그인 - DB 조회
		return likeMapper.selectLikeCountByPostIdOrUserId(postId, userId) > 0 ? true : false;
	}
	
	// input: postId, output: likeCount
	public int getLikeCountByPostId(int postId) {
		return likeMapper.selectLikeCountByPostIdOrUserId(postId, null);
	}
	
	// input: (postId, userId), output: likeCount
	public int getLikeCountByPostIdAndUserId(int postId, int userId) {
		return likeMapper.selectLikeCountByPostIdOrUserId(postId, userId);
	}
	
	// input: (postId, userId), output: X
	public void likeToggle(int postId, int userId) {
		// 조회
		int count = likeMapper.selectLikeCountByPostIdOrUserId(postId, userId);
		
		// 있음 -> 삭제
		if (count > 0) {
			likeMapper.deleteLikeByPostIdAndUserId(postId, userId);
		}
		// 없음 -> 추가
		else {
			likeMapper.insertLikeByPostIdAndUserId(postId, userId);
		}
	}
}
