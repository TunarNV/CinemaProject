package com.example.cinemaprojectwithspring.model.response;

import com.example.cinemaprojectwithspring.model.enums.PaymentStatus;
import com.example.cinemaprojectwithspring.model.enums.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDTO {

    private Long paymentId;
    private Long ticketId;
    private BigDecimal amount;
    private PaymentStatus status;
    private LocalDateTime paymentTime;
    private TicketStatus ticketStatus;

}
