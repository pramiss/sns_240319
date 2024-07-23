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
	
	// input: postId, output: List<Like>
	public int getLikeCountByPostId(int postId) {
		return likeMapper.selectLikeCountByPostId(postId);
	}
	
	// input: (postId, userId), output: boolean
	public boolean isLikedByPostIdAndUserId(int postId, int userId) {
		int count = likeMapper.selectLikeCountByPostIdAndUserId(postId, userId);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	// input: (postId, userId), output: X
	public void likeToggle(int postId, int userId) {
		// 조회
		int count = likeMapper.selectLikeCountByPostIdAndUserId(postId, userId);
		
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
