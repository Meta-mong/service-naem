package com.metamong.metaticket.service.notice;

import com.metamong.metaticket.domain.notice.Notice;
import com.metamong.metaticket.domain.notice.dto.NoticeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

import java.util.List;

public interface NoticeService {

    //공지사한 상세페이지 조회
    NoticeDTO.Notice noticedetail(Long noticeId) throws Exception;

    //공지사항 전체조회
    Page<NoticeDTO.Notice> allNoticeInfo(Pageable pageable)throws Exception;

    //공지사항 등록을 위한 메서드
    public boolean register(NoticeDTO.Notice dto) throws Exception;


    //공지사항 수정하는 메서드
    public Notice updateNotice (NoticeDTO.Notice dto) throws Exception;

    //공지사항 삭제하는 메서드
    public void noticeDelete (Long id) throws Exception;



}
