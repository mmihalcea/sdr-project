package edu.sdr.electronics.service;

import edu.sdr.electronics.domain.StoreOrder;
import edu.sdr.electronics.domain.StoreOrderStatus;
import edu.sdr.electronics.dto.request.OrderRequest;
import edu.sdr.electronics.dto.request.UpdateOrderStatusRequest;
import edu.sdr.electronics.dto.response.AdminOrderResponse;
import edu.sdr.electronics.dto.response.OrderDetailsResponse;
import edu.sdr.electronics.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {

    List<AdminOrderResponse> getAllOrders();

    void updateOrderStatus(UpdateOrderStatusRequest updateOrderStatusRequest);

    StoreOrder saveOrder(OrderRequest orderRequest);

    List<OrderResponse> getUserOrders();

    OrderDetailsResponse getOrderDetails(Long orderId);

    List<StoreOrderStatus> getStatuses();

}
