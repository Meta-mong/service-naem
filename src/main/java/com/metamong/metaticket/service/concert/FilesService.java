package com.metamong.metaticket.service.concert;

import com.metamong.metaticket.domain.concert.Phamplet_File;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FilesService {

    // 파일 이름, 경로
    Phamplet_File saveFile(MultipartFile file) throws IOException;

    Phamplet_File findById(Long id);

    void deleteFile(Long id);

    void updateFile(MultipartFile file,Long id) throws IOException;

}
