package com.mung.mungtique.reservation.adaptor.in.web;

import com.mung.mungtique.reservation.ControllerTestSupport;
import com.mung.mungtique.reservation.adaptor.in.web.dto.ReservationReq;
import com.mung.mungtique.reservation.adaptor.in.web.dto.ReservationRes;
import com.mung.mungtique.reservation.domain.BreedType;
import com.mung.mungtique.reservation.domain.ServiceType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReservationControllerTest extends ControllerTestSupport {

    @DisplayName("mungshop 예약 생성한다.")
    @Test
    void createReservation() throws Exception {
        // given
        ReservationReq reservationReq = ReservationReq.builder()
                .mungShopId(3L)
                .storeName("보라애견샵")
                .dogId(4L)
                .dogName("싼쵸")
                .breedType(BreedType.CHIHUAHUA)
                .serviceType(ServiceType.CUT)
                .reservationDate(LocalDate.of(2025,3,24))
                .reservationTime("12:00")
                .userId(5L)
                .username("이보라")
                .phone("01012345678")
                .requestMessage("잘 부탁드려요~")
                .build();

        when(reservationService.create(reservationReq)).thenReturn(1L);

        // when // then
        mockMvc.perform(
                        post("/api/v1/reservations")
                                .content(objectMapper.writeValueAsString(reservationReq))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1L));
    }

    @DisplayName("예약 번호로 예약 목록 조회한다.")
    @Test
    void getReservation() throws Exception {
        // given
        Long reservationId = 1L;
        ReservationRes reservationRes = ReservationRes.builder()
                                                    .reservationId(reservationId)
                                                    .reservationDate(LocalDate.of(2025,3,24))
                                                    .reservationTime("12:00")
                                                    .build();

        when(reservationService.getReservation(1L)).thenReturn(reservationRes);

        // when // then
        mockMvc.perform(
                        get("/api/v1/reservations/{reservationId}", reservationId)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reservationId").value(reservationId))
                .andExpect(jsonPath("$.reservationDate").value("2025-03-24"))
                .andExpect(jsonPath("$.reservationTime").value("12:00"));
    }

    @DisplayName("특정 유저의 예약 목록 조회한다.")
    @Test
    void getReservationsByUser() throws Exception {
        // given
        Long userId = 1L;
        List<ReservationRes> result = List.of();
        when(reservationService.getReservationsByUser(userId)).thenReturn(result);

        // when // then
        mockMvc.perform(
                        get("/api/v1/reservations/user/{userId}", userId)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @DisplayName("예약 취소 요청을 처리한다.")
    @Test
    void deleteReservation() throws Exception {
        // given
        Long reservationId = 1L;
        when(reservationService.cancelReservation(reservationId)).thenReturn(true);

        // when // then
        mockMvc.perform(delete("/api/v1/reservations/{reservationId}/cancel", reservationId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }
}