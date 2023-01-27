package com.example.final_project.project.order.control;

import com.example.final_project.other.GlobalVariables;
import com.example.final_project.project.order.repository.QuantityPriceDTO;
import com.example.final_project.project.order.repository.entity.Order;
import com.example.final_project.project.product.repository.entity.Product;
import com.example.final_project.project.order.repository.OrderService;
import com.example.final_project.project.order.repository.OrderRepository;
import com.example.final_project.project.user.repository.UserDTO;
import com.example.final_project.project.user.repository.UserIdBalanceDTO;
import com.example.final_project.project.user.repository.UserRepository;
import com.example.final_project.project.user.repository.UserService;
import com.example.final_project.exceptions.IncorrectInputException;
import com.example.final_project.project.product.repository.ProductRepository;
import com.example.final_project.project.auth.control.EmailService;
import com.example.final_project.project.user.repository.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImp implements OrderService {
    OrderRepository orderRepository;
    UserService userService;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private EmailService emailService;

    public OrderServiceImp(OrderRepository orderRepository,
                           UserService userService,
                           ProductRepository productRepository,
                           UserRepository userRepository,
                           EmailService emailService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @Override
    public Order addOrder(Order order) {
        // მოვძებნოთ თუ გვაქვს ასეთი პროდუქტი
        List<Product> products = (List<Product>) order.getProducts();
        Product product = products.get(0);
        Optional<Product> findProductById
                = productRepository.findProductById(product.getId());
        // თუ არ მოიძებნა
        if (findProductById.isEmpty()) {
            throw new IncorrectInputException("Something went wrong!");
        }
        // თუ მოიძებნა წამოვიღოთ დალოგინებული მომხმარებლის ინფორმაცია რომელიც დაგვჭირდება ახალი ორდერის დამატებისას
        UserDTO userByName = userService.getUserByName(GlobalVariables.CURRENT_USER);
        User user = new User(
                userByName.getId(),
                userByName.getName(),
                userByName.getEmail());

        Order order1 = new Order(
                order.getQuantity(),
                order.getDate(),
                "pending",
                order.getProducts(),
                user
        );
        // test Json -->
//        {
//            "quantity": 3,
//                "date": "2023-1-25",
//                "products": [{
//            "id": 1
//        }]
//        }
        return orderRepository.save(order1);
    }

    @Override
    public void increaseOrderQuantityById(long id) {
        Optional<Order> productById = orderRepository.findById(id);
        if (productById.isEmpty()) {
            throw new IncorrectInputException("Something went wrong");
        }
        orderRepository.increaseOrderQuantityById(id);
    }

    @Override
    public void decreaseOrderQuantityById(long id) {
        Optional<Order> productById = orderRepository.findById(id);
        if (productById.isEmpty()) {
            throw new IncorrectInputException("Something went wrong");
        } else if (orderRepository.getOrderQuantityById(id) == 1) { // შევამოწმოთ რაოდენობა თუ 1 ია აღარ დავაკლოთ
            throw new IncorrectInputException("The quantity is minimal");
        }
        orderRepository.decreaseOrderQuantityById(id);
    }

    @Override
    public Optional<Order> getOrderById(long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Integer getOrderQuantityById(long id) {
        return orderRepository.getOrderQuantityById(id);
    }

    @Override
    public void cancelUserOrders() {
        // ამოვიღოთ დალოგინებული მოხმარებელი
        UserDTO userByName = userService.getUserByName(GlobalVariables.CURRENT_USER);
        // წავშალოთ მისი შეკვეთები
        orderRepository.cancelUserOrders(userByName.getId());
    }

    @Override
    public void cancelUserOrderById(long id) {
        // მოხმარებელს მხოლოდ თავისი ორდერის წაშლა შეუძლია შესაბამისი Id - ით (სხვისას ვერ წაშლის :) )
        UserDTO userByName = userService.getUserByName(GlobalVariables.CURRENT_USER);
        Integer productByUserId = orderRepository.getOrderIdWhereUserIdIs(userByName.getId());
        if (productByUserId == null) {
            throw new IncorrectInputException("Something went wrong");
        }
        orderRepository.cancelUserOrderById(id, userByName.getId());
    }

    @Override
    public Integer getOrderIdWhereUserIdIs(long id) {
        return orderRepository.getOrderIdWhereUserIdIs(id);
    }

    @Override
    public void approveOrders() {
        // წამოვიღოთ მომხმარებლის ბალანსი რომელსაც ორდერი აქვს
        List<UserIdBalanceDTO> getAllUserBalance = orderRepository.getUsersBalanceAndEmail();
        // წამოვიღოთ შეკვეთილი ნივთების რაოდენობა და ფასები
        List<QuantityPriceDTO> quantityPrice = orderRepository.OrderQuantity();
        // შევამოწმოთ თუ: ბალანსი > ფასი * რაოდენობაზე სტატუსი გავხადოთ: disapprove თუ არადა approve,
        // გავუგზავნოთ მეილი, დავაკლოთ ბალანსიდან თანხა
        for (int i = 0; i < quantityPrice.size(); i++) {
            BigDecimal totalPrice = quantityPrice.get(i).getPrice().multiply(quantityPrice.get(i).getQuantity());
            BigDecimal balance = getAllUserBalance.get(i).getBalance();
            if (balance.compareTo(totalPrice) >= 0) {
                orderRepository.approveOrders(quantityPrice.get(i).getId());
//                emailService.sendMessage(
//                        getAllUserBalance.get(i).getEmail(),
//                        "Your order",
//                        "Hello, your order Approved, Total price is: " + totalPrice);
//                orderRepository.updateUserBalance(totalPrice, getAllUserBalance.get(i).getId());

            } else {
                orderRepository.disapproveOrders(quantityPrice.get(i).getId());
//                emailService.sendMessage(
//                        getAllUserBalance.get(i).getEmail(),
//                        "Your order",
//                        "Not enough money!" + totalPrice);
            }
        }
    }

    @Override
    public void disapproveOrders(long id) {
        orderRepository.disapproveOrders(id);
    }

    @Override
    public List<UserIdBalanceDTO> getUsersBalanceAndEmail() {
        return orderRepository.getUsersBalanceAndEmail();
    }

    public List<QuantityPriceDTO> OrderQuantity() {
        return orderRepository.OrderQuantity();
    }

    @Override
    public void updateUserBalance(BigDecimal totalPrice, long userId) {
        orderRepository.updateUserBalance(totalPrice, userId);
    }

    @Override
    public void saveAttachment(MultipartFile file, long id) throws IOException {
        Optional<Order> byId = orderRepository.findById(id);
        if(byId.isEmpty()) {
            throw new IncorrectInputException("No such order - " + id);
        }
        byte[] bytes = file.getBytes();
        orderRepository.saveCheckWhereOrderIdIs(bytes, id);
    }
}