package edu.sdr.electronics.controller;

import edu.sdr.electronics.domain.StoreOrderStatus;
import edu.sdr.electronics.dto.request.OrderRequest;
import edu.sdr.electronics.dto.request.UpdateOrderStatusRequest;
import edu.sdr.electronics.dto.response.AdminOrderResponse;
import edu.sdr.electronics.dto.response.OrderDetailsResponse;
import edu.sdr.electronics.dto.response.OrderResponse;
import edu.sdr.electronics.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
public class OrderController {
    private OrderService orderService;

    @PostMapping("/save")
    public void saveOrder(@RequestBody OrderRequest orderRequest) {
        orderService.saveOrder(orderRequest);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AdminOrderResponse>> getAllOrders() {
        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public void updateOrderStatus(@RequestBody UpdateOrderStatusRequest updateOrderStatusRequest) {
        orderService.updateOrderStatus(updateOrderStatusRequest);
    }

    @GetMapping("/user")
    public ResponseEntity<List<OrderResponse>> getUserOrders() {
        return new ResponseEntity<>(orderService.getUserOrders(), HttpStatus.OK);
    }

    @GetMapping("/statuses")
    public ResponseEntity<List<StoreOrderStatus>> getStatuses() {
        return new ResponseEntity<>(orderService.getStatuses(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailsResponse> getOrderDetails(@PathVariable Long id) {
        return new ResponseEntity<>(orderService.getOrderDetails(id), HttpStatus.OK);
    }

}
