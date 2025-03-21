package com.mung.mungtique.reservation.application.port.out.web;


public interface MungShopApiPort {
    boolean lockAndCheckAvailability(Long mungShopId, String reservationTime);
}
