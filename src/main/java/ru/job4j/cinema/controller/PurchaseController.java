package ru.job4j.cinema.controller;

import lombok.Data;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
@Data
public class PurchaseController {

    private final FilmSessionDtoService filmSessionDtoService;
    private final HallService hallService;
    private final FileService fileService;
    private final TicketService ticketService;
    private final UserService userService;
    private final FilmDtoService filmDtoService;

    @GetMapping("/pickFilm/{film.id}")
    public String pickHall(Model model, @PathVariable("film.id") Integer filmId,  HttpSession httpSession) {
        SessionChecker.checkSession(model, httpSession);
        List<FilmSessionDto> availableSessions = filmSessionDtoService.findByIdFilmID(filmId);
        model.addAttribute("filmSessions", availableSessions);
        return "pickHall";
    }

    @GetMapping("/hallPoster/{hall.id}")
    public ResponseEntity<Resource> download(@PathVariable("hall.id") Integer hallId) {
        Hall hall = hallService.findById(hallId);
        byte[] poster = fileService.findByName(hall.getName()).getImg();
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(poster.length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(poster));
    }


    @GetMapping("/pickSession/{filmSession.id}")
    public String pickTicket(Model model,
                             @PathVariable("filmSession.id") Integer filmSessionId,
                             HttpSession httpSession) {
        SessionChecker.checkSession(model, httpSession);
        FilmSessionDto filmSessionDto = filmSessionDtoService.findById(filmSessionId);
        model.addAttribute("filmSession", filmSessionDto);
        Hall currentHall = hallService.findById(filmSessionDto.getHallId());
        List<Integer> rows = new ArrayList<>();
        for (int i = 1; i <= currentHall.getRows(); i++) {
            rows.add(i);
        }
        List<Integer> places = new ArrayList<>();
        for (int j = 1; j <= currentHall.getPlaces(); j++) {
            places.add(j);
        }
        model.addAttribute("rows", rows);
        model.addAttribute("places", places);
        httpSession.setAttribute("filmSessionId", filmSessionDto.getId());
        return "pickTicket";
    }

    @PostMapping("/buyTicket")
    public String buyTicket(@RequestParam("rowId") Integer row,
                            @RequestParam("placeId") Integer place,
                            HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        int filmSessionId = (int) httpSession.getAttribute("filmSessionId");
        Ticket ticket = new Ticket();
        ticket.setRow(row);
        ticket.setPlace(place);
        ticket.setSessionId(filmSessionId);
        ticket.setUserId(user.getId());
        Optional<Ticket> bought = ticketService.addTicket(ticket);
        if (bought.isPresent()) {
            httpSession.setAttribute("ticketId", ticket.getId());
            return "redirect:fin";
        } else {
            return "redirect:bought";
        }

    }

    @GetMapping("/fin")
    public String fin(Model model, HttpSession session) {
        SessionChecker.checkSession(model, session);
        int ticketId = (int) session.getAttribute("ticketId");
        Ticket ticket = ticketService.findById(ticketId);
        FilmSessionDto filmSessionDto = filmSessionDtoService.findById(ticket.getSessionId());
        User user = userService.findById(1).get();
        model.addAttribute("user", user);
        model.addAttribute("ticket", ticket);
        model.addAttribute("filmSession", filmSessionDto);
        return "fin";
    }

    @GetMapping("/bought")
    public String bought() {
        return "bought";
    }


}
