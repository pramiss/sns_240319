package com.sns.timeline.domain;

import java.util.List;

import com.sns.comment.domain.CommentView;
import com.sns.like.domain.Like;
import com.sns.post.entity.PostEntity;
import com.sns.user.entity.UserEntity;

import lombok.Data;
import lombok.ToString;

// View 용 객체 (DB와 매핑x, 화면에 뿌리기 위한 객체)
// 글 1개와 매핑됨
@ToString
@Data
public class CardView {
	// 글 1개
	private PostEntity post;
	
	// 글쓴이 정보
	private UserEntity user;
	
	// 댓글 N개
	private List<CommentView> commentList;
	
	// 좋아요 N개
//	private List<Like> likeList;
	
	// 좋아요를 누른지 여부
}
