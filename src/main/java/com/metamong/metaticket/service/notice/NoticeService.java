package com.metamong.metaticket.service.notice;

import com.metamong.metaticket.domain.notice.Notice;
import com.metamong.metaticket.domain.notice.dto.NoticeDTO;

import java.util.List;

public interface NoticeService {

    //공지사항 전제 초회
    public List<NoticeDTO.Notice> allNoticeInfo() throws Exception;

    //공지사항 등록을 위한 메서드
    public boolean register(NoticeDTO.Notice dto) throws Exception;


    //공지사항 수정하는 메서드
    public Notice updateNotice (NoticeDTO.Notice dto) throws Exception;

    //공지사항 삭제하는 메서드
    public void noticeDelete (Long id) throws Exception;



}
