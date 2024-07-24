package com.sns.timeline;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sns.timeline.bo.TimelineBO;
import com.sns.timeline.domain.CardView;

import jakarta.servlet.http.HttpSession;

@Controller
public class TimelineController {
	
	@Autowired
	private TimelineBO timelineBO;
	
	@GetMapping("/timeline/timeline-view")
	public String timelineView(
			Model model, HttpSession session) {
		Integer sessionUserId = (Integer)session.getAttribute("userId");
		
		// (개선) Card 단위로 가져오기
		List<CardView> cardViewList = timelineBO.generateCardViewList(sessionUserId);
		
		// model
		model.addAttribute("cardViewList", cardViewList);
		
		// 화면이동
		return "timeline/timeline";
	}
}
