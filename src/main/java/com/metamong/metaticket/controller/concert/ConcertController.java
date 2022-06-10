package com.metamong.metaticket.controller.concert;

import com.metamong.metaticket.domain.concert.Concert;
import com.metamong.metaticket.domain.concert.Genre;
import com.metamong.metaticket.domain.concert.Phamplet_File;
import com.metamong.metaticket.domain.concert.dto.ConcertDto;
import com.metamong.metaticket.service.concert.ConcertService;
import com.metamong.metaticket.service.concert.FilesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;



@Controller
@RequiredArgsConstructor
@RequestMapping("/concert")
public class ConcertController {

    private final ConcertService concertService;

    @Autowired
    FilesService filesService;

    // 공연 생성
    @PostMapping("/adminConcert/upload")
    public void addConcert(@Valid ConcertDto dto, @RequestPart("file")MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Phamplet_File files = filesService.saveFile(file);
        Concert concert = ConcertDto.createConcert(dto,files);
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

    @GetMapping("/readImg/{id}")
    public void concertImg(@PathVariable Long id, HttpServletResponse response){
        Phamplet_File files = filesService.findById(id);
        File file = new File(files.getFilePath()+files.getFileOriname());
        FileInputStream fis = null;
        try{
            OutputStream out = response.getOutputStream();
            fis = new FileInputStream(file);
            FileCopyUtils.copy(fis,out);
            out.flush();
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{
                if(fis != null) fis.close();
            }catch (IOException e){

            }
        }
    }

    // 공연 수정
    @PostMapping("/update/{id}")
    public void updateConcert(@PathVariable Long id,@RequestPart("file") MultipartFile file,@ModelAttribute ConcertDto dto,HttpServletRequest request, HttpServletResponse response ) throws Exception {
        Phamplet_File files = filesService.findById(dto.getPhamplet());
        filesService.updateFile(file, dto.getPhamplet());
        concertService.updateConcert(dto,files);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/concert/adminConcert");
        dispatcher.forward(request,response);
    }

    // 공연 삭제
    @PostMapping("/delete/{id}")
    public void deleteConcert(@PathVariable Long id,HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long fileId = concertService.concertInfo(id).getPhamplet();
        concertService.deleteConcert(id);
        filesService.deleteFile(fileId);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/concert/adminConcert");
        dispatcher.forward(request,response);
    }


    // 공연 전체 조회
//    @GetMapping("/adminConcert")
//    public String concertList(Model model, Pageable pageable){
//        model.addAttribute("concert",concertService.concertAllInfo());
//        return "adminConcert";
//    }

    @GetMapping("/adminConcert")
    public String concertList(@PageableDefault(size = 10) Pageable pageable,Model model){
        model.addAttribute("concert",concertService.concertAllInfo(pageable));
        return "adminConcert";
    }

    // 장르별 공연 조회
    @GetMapping("/{genre}")
    public String concertList_Genre(@PageableDefault(size = 16) Pageable pageable ,@PathVariable Genre genre, Model model){
        model.addAttribute("concert",concertService.concertGenreInfo(pageable,genre));
        return "concert";
    }

}
