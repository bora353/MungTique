package com.mung.mungtique.reservation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mung.mungtique.reservation.adaptor.in.web.ReservationController;
import com.mung.mungtique.reservation.application.port.in.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {
        ReservationController.class
})
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected ReservationService reservationService;

}
