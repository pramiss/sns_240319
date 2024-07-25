package com.sns.like.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sns.like.domain.Like;

@Mapper
public interface LikeMapper {
		
	// 조회 - 카운트 쿼리를 하나로 합친다.
	public int selectLikeCountByPostIdOrUserId(
			@Param("postId") int postId,
			@Param("userId") Integer userId);
	
	// 삽입
	public void insertLikeByPostIdAndUserId(
			@Param("postId") int postId,
			@Param("userId") int userId);
	// 삭제
	public void deleteLikeByPostIdAndUserId(
			@Param("postId") int postId,
			@Param("userId") int userId);
	public void deleteLikeByPostId(int postId);
}
