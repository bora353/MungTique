package com.mung.mungtique.reservation.adaptor.out.web;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Component
//@FeignClient(name = "mungshop-service", url = "${mungshop-service-url}") //k8s
@FeignClient(name = "mungshop-service")
public interface MungShopApi {

    @GetMapping("/api/v1/mungshops/{mungShopId}/check-availability")
    boolean lockAndCheckAvailability(@PathVariable("mungShopId") Long mungShopId,
                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate reservationDate,
                                    @RequestParam String reservationTime);
}

