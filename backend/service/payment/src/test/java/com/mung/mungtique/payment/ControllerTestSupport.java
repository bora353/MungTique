package com.mung.mungtique.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mung.mungtique.payment.adaptor.in.web.PaymentController;
import com.mung.mungtique.payment.application.port.in.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {
        PaymentController.class
})
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected PaymentService paymentService;
}
