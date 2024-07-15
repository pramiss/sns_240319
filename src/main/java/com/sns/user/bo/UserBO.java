package com.sns.user.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.user.entity.UserEntity;
import com.sns.user.repository.UserRepository;

@Service
public class UserBO {

	private UserRepository userRepository;
	
	public UserBO(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	// 아이디 중복확인
	// input: loginId, output: UserEntity/null
	public UserEntity getUserEntityByLoginId(String loginId) {
		return userRepository.findByLoginId(loginId);
	} //-- 아이디 중복확인
	
	// 회원가입
	// input: 4params, output: UserEntity/null
	public UserEntity addUser(String loginId, String password, String name, String email) {
		return userRepository.save(UserEntity.builder()
				.loginId(loginId)
				.password(password)
				.name(name)
				.email(email)
				.build());
	} //-- 회원가입
	
	// 로그인
	// input: loginId/password, output: UserEntity/null
	public UserEntity getUserEntityByLoginIdAndPassword(String loginId, String password) {
		return userRepository.findByLoginIdAndPassword(loginId, password);
	} //-- 로그인
}
