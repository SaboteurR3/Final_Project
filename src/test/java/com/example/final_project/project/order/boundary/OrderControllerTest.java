package com.example.final_project.project.order.boundary;

import com.example.final_project.project.auth.control.EmailService;
import com.example.final_project.project.auth.repository.entity.Role;
import com.example.final_project.project.order.repository.OrderRepository;
import com.example.final_project.project.order.repository.entity.Order;
import com.example.final_project.project.product.repository.entity.Product;
import com.example.final_project.project.user.repository.UserDTO;
import com.example.final_project.project.user.repository.UserIdBalanceDTO;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @SpyBean
    private OrderRepository orderRepository;
    @SpyBean
    private UserRepository userRepository;

    public Order createOrderMock(Order order) {
        return order;
    }
    public UserDTO createUserDTOMock(User user) {
        UserDTO userDTO = new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
        return userDTO;
    }
    public UserIdBalanceDTO createUserIdBalanceDTOMock(User user) {
        UserIdBalanceDTO userIdBalanceDTO = new UserIdBalanceDTO();
        userIdBalanceDTO.setId(user.getId());
        userIdBalanceDTO.setBalance(user.getBalance());
        userIdBalanceDTO.setEmail("dummyEmail@gmail.com");
        return userIdBalanceDTO;
    }
    public Order createOrder() {
        Order order = new Order();
        order.setId(1L);
        order.setQuantity(2);
        order.setDate("dummyDate");
        order.setStatus("Pending");
        order.setProducts(List.of(createProduct()));
        order.setUser(createUser());
        return order;
    }

    public User createUser() {
        User user = new User();
        user.setId(1L);
        user.setBalance(BigDecimal.valueOf(500));
        user.setName("dummyName");
        user.setEmail("dummyEmail@gmail.com");
        user.setRoles(List.of(createRole()));
        return user;
    }
    public Role createRole() {
        Role role = new Role();
        role.setId(1L);
        role.setName("ADMIN");
        return role;
    }

    public Product createProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setProduct_name("dummyProductName");
        product.setComplexity("2.5");
        product.setPrice(BigDecimal.valueOf(200));
        return product;
    }

    @Test
    void validOrder_whenAddOrder_thenSuccess() throws Exception {
        Mockito.doReturn(createOrderMock(createOrder())).when(orderRepository).save(Mockito.any());
        Mockito.doReturn(createUserDTOMock(createUser())).when(userRepository).getUserByName(Mockito.any());
        mockMvc.perform(post("/order/addOrder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createOrderMock(createOrder())))
                )
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "USER")
    void invalidAuthority_whenIncreaseOrder_thenForbidden() throws Exception {
        Mockito.doNothing().when(orderRepository).increaseOrderQuantityById(Mockito.anyLong());
        mockMvc.perform(put("/order/incOrderQuantity/" + createOrder().getId()))
                .andExpect(status().isForbidden());
    }

    @Test
    void user_whenCancelUserOrders_thenSuccess() throws Exception {
        Mockito.doReturn(createUserDTOMock(createUser())).when(userRepository).getUserByName(Mockito.any());
        Mockito.doNothing().when(orderRepository).cancelUserOrders(Mockito.anyLong());
        mockMvc.perform(delete("/order/cancelUserOrders"))
                .andExpect(status().isOk());
    }

    @Test
    void user_whenCancelUserOrdersById_thenSuccess() throws Exception {
        Mockito.doReturn(createUserDTOMock(createUser())).when(userRepository).getUserByName(Mockito.any());
        Mockito.doReturn(1).when(orderRepository).getOrderIdWhereUserIdIs(Mockito.anyLong());
        Mockito.doNothing().when(orderRepository).cancelUserOrders(Mockito.anyLong());
        mockMvc.perform(delete("/order/cancelOrdersById/" + createOrder().getId()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void givenValidFileName_whenUploadFile_thenSuccess() throws Exception {
        String fileName = "sampleFile.txt";
        MockMultipartFile sampleFile = new MockMultipartFile(
                "files",
                fileName,
                "text/plain",
                "Some Content".getBytes());

        mockMvc.perform(multipart("/order/uploadFile")
                        .file(sampleFile))
                .andExpect(status().isOk())
                .andExpect(result ->
                        assertTrue(result.getResponse().getContentAsString().contains("Files uploaded successfully!")));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void givenInvalidFileName_whenUploadFile_thenBadRequest() throws Exception {
        String fileName = "sampleFile.txt";
        MockMultipartFile sampleFile = new MockMultipartFile(
                "InvalidFileName",
                fileName,
                "text/plain",
                "Some Content".getBytes());

        mockMvc.perform(multipart("/order/uploadFile")
                        .file(sampleFile))
                .andExpect(status().isBadRequest());
    }
}