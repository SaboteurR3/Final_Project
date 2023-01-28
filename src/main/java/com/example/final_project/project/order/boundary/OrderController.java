package com.example.final_project.project.order.boundary;

import com.example.final_project.project.order.repository.entity.Order;
import com.example.final_project.project.product.repository.ProductService;
import com.example.final_project.project.order.repository.OrderService;
import com.example.final_project.project.user.repository.UserService;
import jakarta.servlet.ServletContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;


@RestController
@RequestMapping("/order")
public class OrderController {
    OrderService orderService;
    ProductService productService;
    UserService userService;

    public OrderController(OrderService orderService,
                           ProductService productService,
                           UserService userService) {
        this.orderService = orderService;
        this.productService = productService;
        this.userService = userService;
    }

    @PostMapping("/addOrder")
    public Order addOrder(@RequestBody Order order) { // 1 ორდერზე ვამატებ 1 ნივთს
        return orderService.addOrder(order);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/incOrderQuantity/{orderId}")
    public void incOrderQuantity(@PathVariable long orderId) {
        orderService.increaseOrderQuantityById(orderId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/decOrderQuantity/{orderId}")
    public void decOrderQuantity(@PathVariable long orderId) {
        orderService.decreaseOrderQuantityById(orderId);
    }

    @DeleteMapping("/cancelUserOrders")
    public void cancelUserOrders() {
        orderService.cancelUserOrders();
    }

    @DeleteMapping("/cancelOrdersById/{orderId}")
    public void cancelOrdersById(@PathVariable long orderId) {
        orderService.cancelUserOrderById(orderId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/approveOrders")
    public void approveOrDisapproveOrders() {
        orderService.approveOrders();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadFile(@RequestParam("files") MultipartFile[] files) {
        orderService.uploadFile(files);
        return new ResponseEntity<>("Files uploaded successfully!", HttpStatus.OK);
    }
}
