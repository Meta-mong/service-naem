package com.metamong.metaticket.service.notice;

import com.metamong.metaticket.domain.admin.Admin;
import com.metamong.metaticket.domain.notice.Notice;
import com.metamong.metaticket.domain.notice.dto.NoticeDTO;
import com.metamong.metaticket.repository.admin.AdminRepository;
import com.metamong.metaticket.repository.notice.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NoticeServiceImpl implements NoticeService{
    @Autowired
    private final NoticeRepository noticeRepository;

    @Autowired
    private AdminRepository adminRepository;

// 객체로 변환
    public Notice dtoToEntity(NoticeDTO.Notice dto){
        Admin admin= adminRepository.findById(dto.getAdminId()).get();
        Notice notice = Notice.builder()
                .admin(admin)
                .classify(dto.getClassify())
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();

        return notice;
    }

    //Board Entity를 Board DTO 클래스로 변환하는 메서드
    public NoticeDTO.Notice entityToDTO(Notice notice){
        NoticeDTO.Notice dto= NoticeDTO.Notice.builder()
                .adminId(notice.getAdmin().getId())
                .classify(notice.getClassify())
                .title(notice.getTitle())
                .content(notice.getContent())
                .regDate(notice.getCreatedDate())
                .modDate(notice.getUpdatedDate())
                .build();

        return dto;
    }



    //공지사항 전체조회
    @Override
    public List<NoticeDTO.Notice> allNoticeInfo() {
         List<NoticeDTO.Notice> noticeList = new ArrayList<>();
        List<Notice> nl = noticeRepository.findAll();
        for(Notice temp: nl){
            NoticeDTO.Notice ndto = entityToDTO(temp);
            noticeList.add(ndto);
        }
        return noticeList;
    }

    //공지사항 등록
    @Override
    public boolean register(NoticeDTO.Notice dto) throws Exception{
        try {
            Notice notice = dtoToEntity(dto);
            noticeRepository.save(notice);
            return true;
        }catch (Exception e) {
            throw e;

        }


    }

// 공지사항 삭제
    @Override
    public void deleteNotice(Long id) {

    }

    // 공지사항 수정
    @Override
    public void modifyNotice(NoticeDTO dto) {

    }
}
