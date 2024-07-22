package com.sns.comment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sns.comment.domain.Comment;

@Mapper
public interface CommentMapper {

	// 댓글목록
	public List<Comment> selectCommentList();
	public List<Comment> selectCommentListByPostId(int postId);
	
	
	// 댓글작성
	public int insertComment(
			@Param("userId") int userId,
			@Param("postId") int postId,
			@Param("content") String content);
}
