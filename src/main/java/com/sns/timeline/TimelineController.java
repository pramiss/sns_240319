package com.sns.timeline;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sns.comment.bo.CommentBO;
import com.sns.comment.domain.Comment;
import com.sns.post.bo.PostBO;
import com.sns.post.entity.PostEntity;

@Controller
public class TimelineController {
	
	@Autowired
	private PostBO postBO;
	
	@Autowired
	private CommentBO commentBO;
	
	@GetMapping("/timeline/timeline-view")
	public String timelineView(Model model) {
		// DB 조회 - post 전체 조회(내림차순)
		List<PostEntity> postList = postBO.getPostEntityList(); 
		
		// DB 조회 - comment 전체 조회(id 내림차순)
		List<Comment> commentList = commentBO.getCommentList();
		
		// Model에 담기
		model.addAttribute("postList", postList);
		model.addAttribute("commentList", commentList);
		
		// 화면이동
		return "timeline/timeline";
	}
}
