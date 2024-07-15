package com.sns.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/user")
@Controller
public class UserController {
	
	/**
	 * 로그인 화면
	 * @return
	 */
	@GetMapping("/sign-in-view")
	public String signInView() {
		return "user/signIn"; 
	} //-- 로그인 화면
	
	/**
	 * 회원가입 화면
	 * @return
	 */
	@GetMapping("/sign-up-view")
	public String signUpView() {
		return "user/signUp";
	} //-- 회원가입 화면
	
	// 로그아웃 API	
	@RequestMapping("/sign-out")
	public String signOut(
			HttpSession session) {
		// 세션정보 삭제
		session.removeAttribute("userId");
		session.removeAttribute("userLoginId");
		session.removeAttribute("userName");
		session.removeAttribute("userProfileIamgeUrl");
		
		// 로그인 화면 리다이렉트
		return "redirect:/user/sign-in-view";
	} //-- 로그아웃 API
}
