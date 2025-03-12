package com.mung.mungtique.payment.application.port.out.web;


public interface MungShopApiPort {
    boolean checkAvailableTime(Long mungShopId, String reservationTime);
}
