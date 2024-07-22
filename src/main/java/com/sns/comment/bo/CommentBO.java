package com.sns.comment.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.comment.domain.Comment;
import com.sns.comment.domain.CommentView;
import com.sns.comment.mapper.CommentMapper;
import com.sns.user.bo.UserBO;
import com.sns.user.entity.UserEntity;

@Service
public class CommentBO {
	
	@Autowired
	private UserBO userBO;
	
	@Autowired
	private CommentMapper commentMapper;
	
	// 댓글조회
	// input: X, output: List<Comment>
	public List<Comment> getCommentList() {
		return commentMapper.selectCommentList();
	}
	
	// 댓글 뷰
	// input: postId, output: List<CommentView>
	public List<CommentView> generateCommentViewListByPostId(int postId) {
		List<Comment> commentList = commentMapper.selectCommentListByPostId(postId);
		List<CommentView> commentViewList = new ArrayList<>();
		
		// commentList -> commentViewList
		for (Comment comment : commentList) {
			// 0. 새 댓글뷰
			CommentView commentView = new CommentView();

			// 1. 댓글 추가
			commentView.setComment(comment);
			
			// 2. 유저 추가
			commentView.setUser(userBO.getUserEntityById(comment.getUserId()));
			
			// 3. commentViewList 추가
			commentViewList.add(commentView);
		}
		
		return commentViewList;
	}
	
	// 댓글작성
	// input: userId, postId, content | output: rowCount
	public int addComment(int userId, int postId, String content) {
		return commentMapper.insertComment(userId, postId, content);
	} //-- 댓글작성
	
	// 댓글삭제
	// input: commentId(id), output: rowCount
	public int deleteCommentById(int id) {
		return commentMapper.deleteCommentById(id);
	} //-- 댓글삭제
}
