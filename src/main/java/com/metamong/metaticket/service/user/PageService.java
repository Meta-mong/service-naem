package com.metamong.metaticket.service.user;

import com.metamong.metaticket.domain.user.dto.UserPage;

public interface PageService {
    //전체 페이지
    public int totalPage(Long totalCnt);
    
    //시작 페이지
    public int startPage(int presentPage);

    //종료 페이지
    public int endPage(int startPage, int totalPage);

    //Pageable 기본 설정
    public void setPage(UserPage userPage, long totalCnt);
}
