package com.metamong.metaticket.service.notice;

import com.metamong.metaticket.domain.admin.Admin;
import com.metamong.metaticket.domain.notice.Notice;
import com.metamong.metaticket.domain.notice.dto.NoticeDTO;
import com.metamong.metaticket.domain.question.Question;
import com.metamong.metaticket.repository.admin.AdminRepository;
import com.metamong.metaticket.repository.notice.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@Service
public class NoticeServiceImpl implements NoticeService{
    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private AdminRepository adminRepository;

// 객체로 변환
    public Notice dtoToEntity(NoticeDTO.Notice dto){
        Admin admin= adminRepository.findById(1L).get();
        Notice notice = Notice.builder()
                .id(dto.getId())
                .admin(admin)
                .classify(dto.getClassify())
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();

        return notice;
    }

    //Board Entity를 Board DTO 클래스로 변환하는 메서드
    public static NoticeDTO.Notice entityToDTO(Notice notice){
        NoticeDTO.Notice dto= NoticeDTO.Notice.builder()
                .id(notice.getId())
                .adminId(notice.getAdmin().getId())
                .classify(notice.getClassify())
                .title(notice.getTitle())
                .content(notice.getContent())
                .createDate(LocalDate.from(notice.getCreatedDate()))
                .updateDate(LocalDate.from(notice.getUpdatedDate()))
                .build();

        return dto;
    }

//공지사항 상세페이지 조회
    @Override
    public NoticeDTO.Notice noticedetail(Long noticeId) throws Exception {

        Notice notice = noticeRepository.findById(noticeId).orElse(null);
        NoticeDTO.Notice noticeDTO = entityToDTO(notice);
        return noticeDTO;
    }

    //공지사항 전체조회
    @Override
    public Page<NoticeDTO.Notice> allNoticeInfo(Pageable pageable) {

            int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
            pageable = PageRequest.of(page, 10);
            Page<Notice> listpage = noticeRepository.findAll(pageable);
            Page<NoticeDTO.Notice> dto = listpage.map(NoticeServiceImpl::entityToDTO);

            return dto;
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

    // 공지사항 수정
    @Override
    public Notice noticeupdate(NoticeDTO.Notice dto) throws Exception {
        Notice updateNotice = noticeRepository.findById(dto.getId()).orElse(null);
        updateNotice.setTitle(dto.getTitle());
        updateNotice.setClassify(dto.getClassify());
        updateNotice.setContent(dto.getContent());

        Notice notice = noticeRepository.save(updateNotice);
        return notice;
    }

    // 공지사항 삭제
    @Override
    public void noticeDelete(Long id) throws Exception {
        Notice noticedelete =  noticeRepository.findById(id).orElseThrow(()->
            new IllegalArgumentException("해당 공지사항 존재하지 않습니다. id=" + id));

        noticeRepository.delete(noticedelete);
    }




}
