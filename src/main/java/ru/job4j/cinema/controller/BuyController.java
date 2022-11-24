package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.BuyService;
import ru.job4j.cinema.service.FilmService;
import ru.job4j.cinema.service.TicketService;
import ru.job4j.cinema.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.*;


@Controller
public class BuyController {

    private final BuyService buyService;
    private final TicketService ticketService;
    private final FilmService filmService;

    private final UserService userService;

    public BuyController(BuyService buyService, TicketService ticketService, FilmService filmService, UserService userService) {
        this.buyService = buyService;
        this.ticketService = ticketService;
        this.filmService = filmService;
        this.userService = userService;
    }


    @GetMapping("/selectRow/{film.id}")
    public String showRows(HttpSession httpSession, Model model, @PathVariable("film.id") int filmId) {
        SessionChecker.checkSession(model, httpSession);
        model.addAttribute("film", filmService.findById(filmId));
        httpSession.setAttribute("film.id", filmId);
        List<Integer> listOfRows = buyService.getRowsList();
        model.addAttribute("listOfRows", listOfRows);
        return "selectRow";
    }

    @PostMapping("/formSelectRow")
    public String chooseRows(HttpSession httpSession, Model model, @RequestParam("rowId") int rowId) {
        SessionChecker.checkSession(model, httpSession);
        int filmId = (int) httpSession.getAttribute("film.id");
        httpSession.setAttribute("rowId", rowId);
        Map<Integer, Ticket> selectTemp = buyService.geMapOfSoldTicketsForRow(filmId, rowId);
        httpSession.setAttribute("ticketMap", selectTemp);
        model.addAttribute("rowId", rowId);
        return "redirect:selectSeat";
    }

    @GetMapping("/selectSeat")
    public String getSeats(Model model, HttpSession httpSession) {
        SessionChecker.checkSession(model, httpSession);
        int filmId = (int) httpSession.getAttribute("film.id");
        model.addAttribute("film", filmService.findById(filmId));
        Integer rowId = (Integer) httpSession.getAttribute("rowId");
        model.addAttribute("rowId", rowId);
        Map<Integer, Ticket> temp = (Map<Integer, Ticket>) httpSession.getAttribute("ticketMap");
        Collection<Integer> freePlaces = buyService.filterForFreePlaces(temp);
        model.addAttribute("freePlaces", freePlaces);
        return "selectSeat";
    }

    @PostMapping("/formSelectSeat")
    public String setSeat(Model model, @RequestParam("seatId") int seatId, HttpSession httpSession) {
        httpSession.setAttribute("seatId", seatId);
        return "redirect:confirm";
    }


    @GetMapping("/confirm")
    public String confirm(Model model, HttpSession httpSession) {
        SessionChecker.checkSession(model, httpSession);
        int filmId = (int) httpSession.getAttribute("film.id");
        System.out.println(filmId);
        int rowId = (int) httpSession.getAttribute("rowId");
        System.out.println(rowId);
        int seatId = (int) httpSession.getAttribute("seatId");
        System.out.println(seatId);
        User user = (User) httpSession.getAttribute("user");
        model.addAttribute("user", user);
        Film film = filmService.findById(filmId);
        Ticket ticket = new Ticket(1, rowId, seatId, user.getId(), filmId);
        model.addAttribute(ticket);
        model.addAttribute("film", film);
        httpSession.setAttribute("ticket", ticket);
        return "confirm";
    }

    @PostMapping("/formConfirm")
    public String buyTicket(Model model, HttpSession httpSession) {
        SessionChecker.checkSession(model, httpSession);
        Ticket ticket = (Ticket) httpSession.getAttribute("ticket");
        buyService.buyTicket(ticket);
        return "redirect:success";
    }
}
