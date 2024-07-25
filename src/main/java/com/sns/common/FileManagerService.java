package com.sns.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FileManagerService {

	// 파일 경로
//	public static final String FILE_UPLOAD_PATH = "C:\\github\\Marondal\\6_project\\sns\\sns_workspace\\images/"; // 집
	public static final String FILE_UPLOAD_PATH = "D:\\배진하\\6_spring_project\\sns\\sns_workspace\\images/"; // 학원
	
	/**
	 * 이미지 업로드
	 * @param file
	 * @param loginId
	 * @return
	 */
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
	
	// 이미지 삭제
	// input: imagePath, output: 0(실패), 1(성공)
	public void deleteFile(String imagePath) {
		// DB경로: /images/spring2_1721374383892/castle.jpg
		// 실제경로: D:\배진하\6_spring_project\sns\sns_workspace\images\spring2_1721374383892
		// path: D:\\배진하\\6_spring_project\\sns\\sns_workspace\\images//images/spring2_1721374383892/castle.jpg

		// 1. path 가져오기
		Path path = Paths.get(FILE_UPLOAD_PATH + imagePath.replace("/images/", "")); 
		
		// 2. 파일(이미지) 삭제
		if (Files.exists(path)) {
			try {
				Files.delete(path);
			} catch (IOException e) {
				log.warn("[FileManagerService 파일삭제] 이미지 파일 삭제 실패, path:{}", path.toString());
				return;
			}
		}
		
		// 3. 파일(디렉토리) 삭제
		path = path.getParent();
		if (Files.exists(path)) {
			try {
				Files.delete(path);
			} catch (IOException e) {
				log.warn("[FileManagerService 파일삭제] 디렉토리 파일 삭제 실패, path:{}", path.toString());
			}
		}
		
	} // 파일(이미지) 삭제
}
