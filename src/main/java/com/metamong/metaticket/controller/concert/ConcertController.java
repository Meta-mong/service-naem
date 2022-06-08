package com.metamong.metaticket.controller.concert;

import com.metamong.metaticket.domain.concert.Concert;
import com.metamong.metaticket.domain.concert.Genre;
import com.metamong.metaticket.domain.concert.dto.ConcertDto;
import com.metamong.metaticket.service.concert.ConcertService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@Controller
@RequiredArgsConstructor
@RequestMapping("/concert")
public class ConcertController {

    private final ConcertService concertService;

    // 공연 생성
    @PostMapping("/adminConcert/upload")
    public void addConcert(@Valid ConcertDto dto, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Concert concert = ConcertDto.createConcert(dto);
        concertService.addConcert(concert);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/concert/adminConcert");
        dispatcher.forward(request,response);
    }

    // 공연 상세내역 조회
    @GetMapping("/{id}")
    public String concertInfo(@PathVariable Long id , Model model){
        ConcertDto concertDto = concertService.concertInfo(id);
        model.addAttribute("concert",concertDto);
        return "concertDetail"; // view 이름
    }

    // 공연 수정
    @PostMapping("/update/{id}")
    public void updateConcert(@PathVariable Long id, @ModelAttribute ConcertDto dto,HttpServletRequest request, HttpServletResponse response ) throws Exception {
        concertService.updateConcert(dto,id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/concert/adminConcert");
        dispatcher.forward(request,response);
    }

    // 공연 삭제
    @PostMapping("/delete/{id}")
    public void deleteConcert(@PathVariable Long id,HttpServletRequest request, HttpServletResponse response) throws Exception {
        concertService.deleteConcert(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/concert/adminConcert");
        dispatcher.forward(request,response);
    }


    // 공연 전체 조회
    @GetMapping("/adminConcert")
    public String concertList(Model model){
        model.addAttribute("concert",concertService.concertAllInfo());
        return "adminConcert";
    }

    // 장르별 공연 조회
    @GetMapping("/{genre}")
    public String concertList_Genre(@PathVariable Genre genre, Model model){
        model.addAttribute("concert",concertService.concertGenreInfo(genre));
        return "concert";
    }

}
