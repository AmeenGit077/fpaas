package com.fpass.service.services;

import com.fpass.service.entity.Booking;
import com.fpass.service.entity.Ticket;
import com.fpass.service.entity.User;
import com.fpass.service.repository.BookingRepository;
import com.fpass.service.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final BookingRepository bookingRepository;
    private final QRCodeService qrCodeService;

    public TicketService(TicketRepository ticketRepository, BookingRepository bookingRepository, QRCodeService qrCodeService) {
        this.ticketRepository = ticketRepository;
        this.bookingRepository = bookingRepository;
        this.qrCodeService = qrCodeService;
    }

    public Booking bookTicket(User user, Long ticketId) throws Exception {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        String qrPath = qrCodeService.generateQRCode("User:" + user.getId() + ", Ticket:" + ticket.getId());

        Booking booking = Booking.builder()
                .user(user)
                .ticket(ticket)
                .qrCodePath(qrPath)
                .bookingTime(LocalDateTime.now())
                .build();

        return bookingRepository.save(booking);
    }
}
