package com.fpass.service.DAO;

import com.fpass.service.entity.Ticket;
import com.fpass.service.entity.Users;
import com.fpass.service.exceptionHandler.AppException;
import com.fpass.service.repository.TicketRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TicketDAO {


    private final TicketRepository ticketRepository;

    public TicketDAO(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket getTickets(Users users) throws AppException {

        return ticketRepository.findById(users.getId())
                .orElseThrow(() -> AppException.ticketNotFound(users.getUsername() + " tickets not found"));
    }
}
