package com.mung.mungtique.reservation.application.port.out.web;


import java.time.LocalDate;

public interface MungShopApiPort {
    boolean lockAndCheckAvailability(Long mungShopId, LocalDate reservationDate, String reservationTime);
}
