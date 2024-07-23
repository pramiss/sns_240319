package com.sns.timeline.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.comment.bo.CommentBO;
import com.sns.like.bo.LikeBO;
import com.sns.like.domain.Like;
import com.sns.post.bo.PostBO;
import com.sns.post.entity.PostEntity;
import com.sns.timeline.domain.CardView;
import com.sns.user.bo.UserBO;

@Service
public class TimelineBO {
	
	@Autowired
	private PostBO postBO;
	
	@Autowired
	private UserBO userBO;
	
	@Autowired
	private CommentBO commentBO;
	
	@Autowired
	private LikeBO likeBO;
	
	// Timeline의 전체 Card들을 가지고 옴
	// input: X, output: List<CardView>
	public List<CardView> generateCardViewList(Integer sessionUserId) {
		List<CardView> cardViewList = new ArrayList<>();
		List<PostEntity> postEntityList = postBO.getPostEntityList();
		
		// postEntityList => (PostEntity -> CardView) => cardViewList에 저장
		for (PostEntity post : postEntityList) {
			// 0) 추가할 Card
			CardView card = new CardView();
			int postId = post.getId(); // comment, like
			int userId = post.getUserId(); // user
			
			// 1) 글 추가
			card.setPost(post);
			
			// 2) 글쓴이 추가
			card.setUser(userBO.getUserEntityById(userId));
			
			// 3) 댓글뷰 N개 추가
			card.setCommentList(commentBO.generateCommentViewListByPostId(postId));
			
			// 4) 좋아요리스트 추가
			card.setLikeCount(likeBO.getLikeCountByPostId(postId));
			
			// 5) 유저의 좋아요 누른 여부
			if (sessionUserId == null) sessionUserId = 0;
			card.setLiked(likeBO.isLikedByPostIdAndUserId(postId, sessionUserId));
			
			// !!! 반드시 리스트에 넣는다.
			cardViewList.add(card);
		}
		
		return cardViewList;
	}
}
