package com.metamong.metaticket.service.user;

import com.metamong.metaticket.domain.user.dto.UserPage;
import org.springframework.stereotype.Service;

@Service
public class PageServiceImpl implements PageService{

    @Override
    public int totalPage(Long totalCnt) {
        if (totalCnt % 10 == 0) return (int) (totalCnt / 10);
        return (int) (totalCnt / 10 + 1);
    }

    @Override
    public int startPage(int presentPage) {
        int startPage=0;
        if(presentPage<5) return 1;
        if(presentPage%5==0){
            startPage = 5*(presentPage/5-1)+1;
        }else{
            startPage = 5*(presentPage/5)+1;
        }
        return startPage;
    }

    @Override
    public int endPage(int startPage, int totalPage) {
        int endPage = startPage+4;
        if(totalPage<endPage)endPage = totalPage;
        return endPage;
    }

    @Override
    public void setPage(UserPage userPage, long totalCnt) {
        int totalPage = totalPage(totalCnt);
        int startPage = startPage(userPage.getPresentPage());
        int endPage = endPage(startPage, totalPage);
        userPage.setTotalPage(totalPage);
        userPage.setStartPage(startPage);
        userPage.setEndPage(endPage);
    }

}
