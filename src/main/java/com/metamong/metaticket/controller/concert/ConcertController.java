package com.metamong.metaticket.controller.concert;

import com.metamong.metaticket.domain.concert.Concert;
import com.metamong.metaticket.dto.concert.ConcertDto;
import com.metamong.metaticket.service.concert.ConcertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


//@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/concert")
public class ConcertController {

    private final ConcertService concertService;

    // 공연 생성

    @PostMapping("/admin/create")
    public ResponseEntity<Void> addConcert(@Valid ConcertDto dto){
        Concert concert = ConcertDto.createConcert(dto);
        concertService.addConcert(concert);
//        ConcertDto concertDto = concertService.concertInfo(concert.getId());
        return ResponseEntity.created(URI.create("/concert/"+concert.getId())).build();
    }


    // 공연 상세내역 조회

    @GetMapping("/{id}")
    public ConcertDto concertInfo(@PathVariable Long id){
        return concertService.concertInfo(id);
    }


    // 공연 수정

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateConcert(@PathVariable Long id, @Valid ConcertDto dto ){
        concertService.updateConcert(dto,id);
        return new ResponseEntity(HttpStatus.OK);
    }

    // 공연 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConcert(@PathVariable Long id){
        concertService.deleteConcert(id);
        return new ResponseEntity(HttpStatus.OK);
    }


    // 공연 전체 조회
    @GetMapping("/admin")
    public List<Concert> concertList(){
        List<Concert> concert = concertService.concertAllInfo();
        return concert;
    }

}
