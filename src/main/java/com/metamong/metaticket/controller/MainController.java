package com.metamong.metaticket.controller;

import com.metamong.metaticket.domain.concert.Concert;
import com.metamong.metaticket.domain.concert.Genre;
import com.metamong.metaticket.domain.concert.dto.ConcertDto;
import com.metamong.metaticket.service.concert.ConcertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    ConcertService concertService;

    @GetMapping("/")
    public String main(Model model){
        List<ConcertDto> opentickets = concertService.openTickets();
        model.addAttribute("opentickets", opentickets);
        return "index";
    }

    @GetMapping("/ticketopen")
    public String ticketOpen(Model model){
        List<ConcertDto> opentickets = concertService.openTickets();
        model.addAttribute("opentickets", opentickets);
        List<ConcertDto> openticketList = concertService.allOpenTickets();
        model.addAttribute("openticketList", openticketList);
        return "/ticketopen/ticket_open";
    }

    @PostMapping("/ticketopen/searchoption")
    public Map<String, Object> ticketopenSearchOption(@RequestParam("genre") Genre genre, @RequestParam("title") String title, Model model){
        Map<String, Object> map = new HashMap<>();
        try {
            List<ConcertDto> opentickets = concertService.openTicketsOptions(genre, title);
            model.addAttribute("openticketList", opentickets);
            map.put("result", true);
        }catch (Exception e){
            map.put("result", false);
        }
        return map;
    }

}
