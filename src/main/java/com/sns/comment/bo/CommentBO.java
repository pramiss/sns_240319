package com.sns.comment.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.comment.domain.Comment;
import com.sns.comment.mapper.CommentMapper;

@Service
public class CommentBO {
	
	@Autowired
	private CommentMapper commentMapper;
	
	// 댓글조회
	// input: X, output: List<Comment>
	public List<Comment> getCommentList() {
		return commentMapper.selectCommentList();
	}
	
	// 댓글작성
	// input: userId, postId, content | output: rowCount
	public int addComment(int userId, int postId, String content) {
		return commentMapper.insertComment(userId, postId, content);
	} //-- 댓글작성
}
