package com.example.cinemaprojectwithspring.service.impl;

import com.example.cinemaprojectwithspring.entity.Ticket;
import com.example.cinemaprojectwithspring.service.BookingFacadeService;
import com.example.cinemaprojectwithspring.service.BookingService;
import com.example.cinemaprojectwithspring.service.PaymentService;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * todo
 *  Class AI ilə yazılmayıb ki? Çünki, facade patterni istifadə edilib.
 *  Ən azından müəllim sual versə məlumatın olsun
 */
@Service
@RequiredArgsConstructor
public class BookingFacadeServiceImpl implements BookingFacadeService {

    private final BookingService bookingService;
    private final PaymentService paymentService;

    /*
        todo
          normalda ödəniş edilmə prosesi bu formada olmalı deyil.
          Məsələn, user ilk növbədə book edir, onu statusunu reserved edilir.
          front PaymentController-lə müraciət edir və ödəniş uğurlu olduqda booking status olur confirmed və paymentStatus olur uğurlu success və s.
     */
    @Override
    @Transactional
    public void bookAndPay(Long sessionId, Long seatId, Long userId) {

        Ticket ticket = bookingService.reserve(sessionId, seatId, userId);
        paymentService.pay(ticket.getId());

    }
}
