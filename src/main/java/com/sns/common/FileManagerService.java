package com.sns.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileManagerService {

	// 파일 경로
//	public static final String FILE_UPLOAD_PATH = "C:\\github\\Marondal\\6_project\\sns\\sns_workspace\\images/"; // 집
	public static final String FILE_UPLOAD_PATH = "D:\\배진하\\6_spring_project\\sns\\sns_workspace\\images/"; // 학원
	
	public String uploadFile(MultipartFile file, String loginId) {
		// 1. 이미지 디렉토리명 생성 => "sun_123"
		String directoryName = loginId + "_" + System.currentTimeMillis();
				
		// 2. 최종 디렉토리명 생성 => "C:\\...\\images/sun_123/"
		String filePath = FILE_UPLOAD_PATH + directoryName + "/";
		
		// 3. 최종 디렉토리 실제 생성
		File directory = new File(filePath);
		if (directory.mkdir() == false) {
			// 디렉토리 생성 실패
			return null;
		}
		
		// 4. 최종 디렉토리에 file 업로드
		try {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(filePath + file.getOriginalFilename()); // 최종경로+파일명 (찐 최종경로)
			Files.write(path, bytes);
		} catch (IOException e) {
			// 파일 업로드 실패
			e.printStackTrace();
			return null;
		}
		
		// 5. 실제경로-DB경로 매핑 (WebMvcConfig 클래스 자동 매핑)
		
		// 6. 리턴 "/images/directoryName/fileName"
		return "/images/" + directoryName + "/" + file.getOriginalFilename();
	} //-- uploadFile
}
