package com.mung.mungtique.reservation.application.port.out.web;


public interface MungShopApiPort {
    boolean checkAvailableTime(Long mungShopId, String reservationTime);
}
