package com.mung.mungtique.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mung.mungtique.user.adaptor.in.web.EmailController;
import com.mung.mungtique.user.adaptor.in.web.ReissueController;
import com.mung.mungtique.user.adaptor.in.web.UserController;
import com.mung.mungtique.user.application.port.in.EmailService;
import com.mung.mungtique.user.application.port.in.TokenService;
import com.mung.mungtique.user.application.port.in.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(controllers = {
        UserController.class,
        EmailController.class,
        ReissueController.class,
})
@WithMockUser
@ActiveProfiles("test")
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected UserService userService;

    @MockBean
    protected EmailService emailService;

    @MockBean
    protected TokenService tokenService;

    @MockBean
    protected HttpServletRequest request;

    @MockBean
    protected HttpServletResponse response;

    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .defaultRequest(post("/**").with(csrf()))
                .defaultRequest(patch("/**").with(csrf()))
                .defaultRequest(delete("/**").with(csrf()))
                .build();
    }
}
