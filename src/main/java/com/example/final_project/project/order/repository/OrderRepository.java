package com.example.final_project.project.order.repository;

import com.example.final_project.project.order.repository.entity.Order;
import com.example.final_project.project.user.repository.UserIdBalanceDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Transactional
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Modifying
    @Query(value = "UPDATE orders set orders_quantity += 1  where orders_id = :id", nativeQuery = true)
    void increaseOrderQuantityById(@Param("id") long id);

    @Modifying
    @Query(value = "UPDATE orders set orders_quantity -= 1  where orders_id = :id", nativeQuery = true)
    void decreaseOrderQuantityById(@Param("id") long id);

    @Query(value = "select orders_quantity from orders where orders_id = :id", nativeQuery = true)
    Integer getOrderQuantityById(@Param("id") long id);

    @Modifying
    @Query(value =
            "delete from orders \n" +
            "where orders_id in( \n" +
            "select orders_id \n" +
            "from orders\n" +
            "where user_id = :id)"
            , nativeQuery = true)
    void cancelUserOrders(@Param("id") long id);

    @Modifying
    @Query(value =
            "delete from orders \n" +
            "where orders_id in( \n" +
            "select orders_id \n" +
            "from orders\n" +
            "where orders_id = :id and user_id = :userID)"
            , nativeQuery = true)
    void cancelUserOrderById(@Param("id") long id, @Param("userID") long userID);

    @Query(value = "select orders_id from orders where user_id = :userId", nativeQuery = true)
    Integer getOrderIdWhereUserIdIs(@Param("userId") long userId);

    @Modifying
    @Query(value = "update orders set order_status = 'approved' where order_status = 'pending' and " +
            "orders_id = :orderId", nativeQuery = true)
    void approveOrders(@Param("orderId") long id);

    @Modifying
    @Query(value = "update orders set order_status = 'disapproved' where order_status = 'pending' and " +
            "orders_id = :orderId", nativeQuery = true)
    void disapproveOrders(@Param("orderId") long id);


    @Query("Select new com.example.final_project.project.user.repository.UserIdBalanceDTO(u.id, u.balance, u.email) from User u " +
            "join Order v on u.id = v.user.id where v.status = 'pending'")
    List<UserIdBalanceDTO> getUsersBalanceAndEmail();

    @Modifying
    @Query(value = "update users set users_balance -= :totalPrice where users_id = :userId", nativeQuery = true)
    void updateUserBalance(@Param("totalPrice") BigDecimal totalPrice, @Param("userId") long userId);

    @Query(value =
            "Select orders_quantity as quantity, product_price as price, E.orders_id as id " +
            "from orders E " +
            "join products_orders F on E.orders_id = F.orders_id " +
            "join products V on V.products_id = F.products_id " +
            "where order_status = 'pending'"
            ,nativeQuery = true)
    List<QuantityPriceDTO> OrderQuantity();
}
