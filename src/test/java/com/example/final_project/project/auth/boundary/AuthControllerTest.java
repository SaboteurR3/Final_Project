package com.example.final_project.project.auth.boundary;

import com.example.final_project.project.auth.repository.LoginDTO;
import com.example.final_project.project.auth.repository.RegistrationDTO;
import com.example.final_project.project.user.repository.UserRepository;
import com.example.final_project.project.user.repository.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @SpyBean
    private UserRepository userRepository;

    private Object createLoginDTOMock(LoginDTO loginDTO) {
        User user = new User();
        user.setId(1L);
        user.setName(loginDTO.getName());
        user.setEmail("dummyEmail@gmail.com");
        user.setBalance(BigDecimal.valueOf(500));
        user.setPassword(loginDTO.getPassword());
        user.setRoles(Collections.singletonList(null));
        user.setOrders(Collections.singletonList(null));
        return user;
    }

    private LoginDTO createLoginDTOMock() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setName(null);
        loginDTO.setPassword("password");
        return loginDTO;
    }

    private RegistrationDTO createRegistrationDTOMock() {
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setName("+++++++");
        registrationDTO.setEmail("dummyEmail@gmail.com");
        registrationDTO.setPassword("dummyPassword");
        return registrationDTO;
    }

    @Test
    void givenInvalidCredentials_whenLogin_thenBadRequest() throws Exception {
        Mockito.doReturn(createLoginDTOMock(createLoginDTOMock())).when(userRepository).getUserByUsername(Mockito.any());
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createLoginDTOMock())))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertTrue(result.getResponse().getContentAsString().contains("Username mustn't be empty!")));
    }

    @Test
    void givenInvalidCredentials_whenRegister_thenBadRequest() throws Exception {
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRegistrationDTOMock())))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertTrue(result.getResponse().getContentAsString().contains("must match")));
    }
}
