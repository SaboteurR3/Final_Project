package com.example.final_project.project.auth.boundary;

import com.example.final_project.project.auth.repository.LoginDTO;
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

import java.math.BigDecimal;
import java.util.*;
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

    @Test
    void givenValidCredentials_whenLogin_thenSuccess() throws Exception {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setName("dummyName");
        loginDTO.setPassword("dummyPassword");
        Mockito.doReturn(createLoginDTOMock(loginDTO)).when(userRepository).getUserByUsername(Mockito.any());
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
//                .andExpect(result -> assertEquals("User logged in successfully"), )
    }


















    @Test
    void register() {
    }
//    void givenInvalidUsername_whenLogin_thenBadRequest() throws Exception
}