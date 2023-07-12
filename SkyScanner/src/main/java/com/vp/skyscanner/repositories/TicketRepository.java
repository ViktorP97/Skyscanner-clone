package com.vp.skyscanner.repositories;

import com.vp.skyscanner.models.Ticket;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

  Optional<Ticket> findById(Long id);
}