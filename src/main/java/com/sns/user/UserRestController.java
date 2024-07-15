package com.sns.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sns.common.EncryptUtils;
import com.sns.user.bo.UserBO;
import com.sns.user.entity.UserEntity;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/user")
@RestController
public class UserRestController {

	private UserBO userBO;
	
	public UserRestController(UserBO userBO) {
		this.userBO = userBO;
	}
	
	/**
	 * 아이디 중복 확인 API
	 * @param loginId
	 * @return
	 */
	@GetMapping("/is-duplicated-id")
	public Map<String, Object> isDuplicatedId(
			@RequestParam("loginId") String loginId) {
		// DB SELECT - JPA
		UserEntity user = userBO.getUserEntityByLoginId(loginId);
		
		// AJAX 응답
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		
		if (user != null) { // 중복O
			result.put("is_duplicated", true);			
		} else { // 중복X
			result.put("is_duplicated", false);
		}
		
		return result;
	}
	
	/**
	 * 회원가입 API
	 * @param loginId
	 * @param password
	 * @param name
	 * @param email
	 * @return
	 */
	@PostMapping("/sign-up")
	public Map<String, Object> signUp(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			@RequestParam("name") String name,
			@RequestParam("email") String email) {
		// 비밀번호 암호화 (md5)
		String hashedPassword = EncryptUtils.md5(password);
		
		// 회원가입
		UserEntity user = userBO.addUser(loginId, hashedPassword, name, email);
		
		// AJAX response
		Map<String, Object> result = new HashMap<>();
		if (user != null) {
			result.put("code", 200);
			result.put("result", "성공");	
		} else {
			result.put("code", 500);
			result.put("error_message", "회원가입에 실패했습니다.");
		}
		
		return result;
	} //-- 회원가입 API
	
	/**
	 * 로그인 API
	 * @param loginId
	 * @param password
	 * @param session
	 * @return
	 */
	@PostMapping("/sign-in")
	public Map<String, Object> signIn(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			HttpSession session) {
		// hashed password
		String hashedPassword = EncryptUtils.md5(password);
		
		// DB 조회: loginId, hashedPassword
		UserEntity user = userBO.getUserEntityByLoginIdAndPassword(loginId, hashedPassword);
		
		// 로그인 정보 세션에 저장
		Map<String, Object> result = new HashMap<>();

		if (user != null) { // 로그인 성공
			// 세션에 사용자 정보 저장
			session.setAttribute("userId", user.getId());
			session.setAttribute("userLoginId", user.getLoginId());
			session.setAttribute("userName", user.getName());
			session.setAttribute("userProfileIamgeUrl", user.getProfileIamgeUrl());
			
			result.put("code", 200);
			result.put("result", "성공");
		} else {
			result.put("code", 403);
			result.put("error_message", "로그인 정보를 확인해주세요.");
		}
		
		// AJAX return
		return result;
	} //-- 로그인 API
}
