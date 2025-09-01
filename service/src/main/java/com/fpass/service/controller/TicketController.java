package com.fpass.service.controller;

import com.fpass.service.DAO.TicketDAO;
import com.fpass.service.customAnnotation.CurrentUser;
import com.fpass.service.entity.Ticket;
import com.fpass.service.entity.Users;
import com.fpass.service.repository.TicketRepository;
import com.fpass.service.services.QRCodeService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketRepository ticketRepository;

    private final TicketDAO ticketDAO;

    private final QRCodeService qrCodeService;

    @Autowired
    public TicketController(TicketRepository ticketRepository, TicketDAO ticketDAO, QRCodeService qrCodeService) {
        this.ticketRepository = ticketRepository;
        this.ticketDAO = ticketDAO;
        this.qrCodeService = qrCodeService;
    }

    @PostMapping("/book")
    public Ticket bookTicket(@RequestParam String username,
                             @RequestParam String source,
                             @RequestParam String destination,
                             @CurrentUser Users users) throws Exception {


        Ticket ticket = Ticket.builder()
                .source(source)
                .destination(destination)
                .bookingTime(LocalDateTime.now())
                .users(users)
                .build();

        // Generate QR Code
        //String filePath = "qrcodes/ticket_" + System.currentTimeMillis() + ".png";

        String qrPath = qrCodeService.generateQRCode("User:" + users.getId() + ", Ticket:" + ticket.getId());
        ticket.setQrCodePath(qrPath);

        return ticketRepository.save(ticket);
    }

    @GetMapping("")
    public Ticket getTicketById(@Parameter(hidden = true) @CurrentUser Users users)  {

        return ticketDAO.getTickets(users);
    }
}
