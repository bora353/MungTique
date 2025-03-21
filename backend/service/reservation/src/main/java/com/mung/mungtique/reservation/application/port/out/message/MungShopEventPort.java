package com.mung.mungtique.reservation.application.port.out.message;

import com.mung.mungtique.reservation.adaptor.out.message.dto.MungShopConfirmMessage;

public interface MungShopEventPort {

    void sendReservationConfirmToMungShop(MungShopConfirmMessage event);
}
