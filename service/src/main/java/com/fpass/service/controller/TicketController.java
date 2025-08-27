package com.fpass.service.controller;

import com.fpass.service.entity.Ticket;
import com.fpass.service.entity.User;
import com.fpass.service.repository.TicketRepository;
import com.fpass.service.repository.UserRepository;
import com.fpass.service.services.QRCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired private UserRepository userRepository;

    private final QRCodeService qrCodeService;

    public TicketController(QRCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @PostMapping("/book")
    public Ticket bookTicket(@RequestParam String username,
                             @RequestParam String source,
                             @RequestParam String destination) throws Exception {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Ticket ticket = Ticket.builder()
                .source(source)
                .destination(destination)
                .bookingTime(LocalDateTime.now())
                .user(user)
                .build();

        // Generate QR Code
        String filePath = "qrcodes/ticket_" + System.currentTimeMillis() + ".png";

        String qrPath = qrCodeService.generateQRCode("User:" + user.getId() + ", Ticket:" + ticket.getId());
        ticket.setQrCodePath(qrPath);

        return ticketRepository.save(ticket);
    }
}
