package com.metamong.metaticket.service.concert;

import com.metamong.metaticket.domain.concert.Phamplet_File;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@SpringBootTest
public class FilesServiceTest {

    @Autowired
    FilesService filesService;

    @Autowired
    ServletContext servletContext;

    // 파일 저장
    @Test
    public void 파일저장() throws IOException {
        MultipartFile multipartFile = new MockMultipartFile("test.png",
                new FileInputStream(new File(servletContext.getRealPath("/uploadImg")+"/test.png")));
        Phamplet_File files = filesService.saveFile(multipartFile);
        System.out.println(files.toString());
    }

    // 파일 조회
    @Test
    public void 파일조회(){
        Phamplet_File files = filesService.findById(2L);
        System.out.println(files.toString());
    }

    // 파일 수정
    @Test
    public void 파일수정() throws IOException {
        MultipartFile multipartFile = new MockMultipartFile("test1.png",
                new FileInputStream(new File(servletContext.getRealPath("/uploadImg")+"/test1.png")));
        filesService.updateFile(multipartFile,2L);
    }

    // 파일 삭제
    @Test
    public void 파일삭제(){
        filesService.deleteFile(2L);
    }
}
