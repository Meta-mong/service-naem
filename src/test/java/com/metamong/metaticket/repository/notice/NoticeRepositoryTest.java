package com.metamong.metaticket.repository.notice;

import com.metamong.metaticket.domain.admin.Admin;
import com.metamong.metaticket.domain.notice.Notice;
import com.metamong.metaticket.domain.notice.dto.NoticeDTO;
import com.metamong.metaticket.repository.admin.AdminRepository;
import com.metamong.metaticket.repository.notice.NoticeRepository;

import com.metamong.metaticket.service.notice.NoticeService;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@PropertySource("classpath:application.yml")
class NoticeRepositoryTest {

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private NoticeService noticeService;

    @Test
    @DisplayName("공시사항 삽입 테스트1")
    public void InsertTest(){
        Admin admin = adminRepository.findById(2L).get();

        Notice notice = Notice.builder().
                admin(admin).
                classify("테스트").
                title("테스트").
                content("테스트").
                build();

        Notice temp = noticeRepository.save(notice);
        System.out.println(temp.toString());
    }

    @Test
    @DisplayName("공지사항 수정 테스트")
    public void updateTest() throws Exception{

        Notice findNotice = noticeRepository.findById(1L).get();
        findNotice.update("테스트1","테스트2","테스트3");
        Notice updateNotice = noticeRepository.save(findNotice);

        assertAll(
                () -> assertEquals(updateNotice.getTitle(),"테스트2"),
                () -> assertEquals(updateNotice.getContent(), "테스트3"),
                () -> assertEquals(updateNotice.getClassify(),"테스트1")
        );
    }

    @Test
    @DisplayName("공지사항 삭제 테스트")
    public void deleteTest(){
        Optional<Notice> notice = noticeRepository.findById(1L);
        Assert.assertTrue(notice.isPresent());


        notice.ifPresent(selectNotice ->{
            noticeRepository.delete(selectNotice);
        });

        Optional<Notice> deletedNotice = noticeRepository.findById(1L);

        Assert.assertFalse(deletedNotice.isPresent());

    }


    @Test
    @DisplayName("공지사항 조회 테스트")
    public void selectTest(){
        Long id = 1L;
        Optional<Notice> result = noticeRepository.findById(id);

        if(result.isPresent()){
            Notice notice = result.get();
            assertEquals(notice.getId(),1L);

            System.out.println(notice.toString());
        }

    }

    @Test
    @DisplayName(value = "공지사항 등록")
    public void register()throws Exception{
        NoticeDTO.Notice dto = new NoticeDTO.Notice();
        dto.setAdminId(1L);
        dto.setClassify("테스트");
        dto.setTitle("테스트");
        dto.setContent("테스트");
        dto.setModDate(LocalDateTime.now());
        dto.setRegDate(LocalDateTime.now());

        boolean result = noticeService.register(dto);

        System.out.println(result);





    }

}

