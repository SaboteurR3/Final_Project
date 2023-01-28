package com.example.final_project.project.order.repository;

import com.example.final_project.project.order.repository.entity.Order;
import com.example.final_project.project.user.repository.UserIdBalanceDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order addOrder(Order order);
    void increaseOrderQuantityById(long id);
    void decreaseOrderQuantityById(long id);
    Optional<Order> getOrderById(long id);
    Integer getOrderQuantityById(long id);
    void cancelUserOrders();
    void cancelUserOrderById(long id);
    Integer getOrderIdWhereUserIdIs(long id);
    void approveOrders();
    void disapproveOrders(long id);
    List<UserIdBalanceDTO> getUsersBalanceAndEmail();
    void updateUserBalance(BigDecimal totalPrice, long userId);
    void uploadFile(MultipartFile[] files);
}
