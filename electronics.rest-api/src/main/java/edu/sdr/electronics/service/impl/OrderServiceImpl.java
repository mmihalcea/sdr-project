package edu.sdr.electronics.service.impl;

import edu.sdr.electronics.domain.*;
import edu.sdr.electronics.dto.request.OrderLineRequest;
import edu.sdr.electronics.dto.request.OrderRequest;
import edu.sdr.electronics.dto.request.UpdateOrderStatusRequest;
import edu.sdr.electronics.dto.response.*;
import edu.sdr.electronics.repository.ProductRepository;
import edu.sdr.electronics.repository.StoreOrderRepository;
import edu.sdr.electronics.repository.StoreOrderStatusRepository;
import edu.sdr.electronics.repository.StoreUserRepository;
import edu.sdr.electronics.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
@Log4j2
public class OrderServiceImpl implements OrderService {

    private final StoreOrderRepository storeOrderRepository;
    private final StoreUserRepository storeUserRepository;
    private final ProductRepository productRepository;

    private final StoreOrderStatusRepository storeOrderStatusRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<AdminOrderResponse> getAllOrders() {
        List<AdminOrderResponse> orders = new ArrayList<>();
        storeOrderRepository.findAll().forEach(o -> {
            AdminOrderResponse adminOrderResponse = getOrderResponse(o, AdminOrderResponse.class);
            adminOrderResponse.setUser(modelMapper.map(o.getStoreUser(), OrderUserResponse.class));
            orders.add(adminOrderResponse);
        });
        return orders;
    }

    @Override
    public void updateOrderStatus(UpdateOrderStatusRequest updateOrderStatusRequest) {
        storeOrderRepository.findById(updateOrderStatusRequest.getOrderId()).ifPresent(storeOrder -> {
            storeOrder.setOrderStatus(new StoreOrderStatus(updateOrderStatusRequest.getOrderStatus()));
            storeOrderRepository.save(storeOrder);
        });
    }

    @Override
    public StoreOrder saveOrder(OrderRequest orderRequest) {

        StoreOrder order = new StoreOrder();
        order.setOrderDate(LocalDateTime.now());
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        StoreUser storeUser = storeUserRepository.findByUsername(principal.getName()).orElse(null);
        order.setStoreUser(storeUser);
        StoreOrderStatus orderStatus = new StoreOrderStatus(1);
        order.setOrderStatus(orderStatus);
        BigDecimal totalPrice = new BigDecimal(0);
        List<OrderLine> orderLines = new ArrayList<>();
        for (OrderLineRequest ol : orderRequest.getOrderLines()) {
            Product product = productRepository.findById(ol.getProductId()).orElse(null);
            if (product != null) {
                OrderLine orderLine = new OrderLine();
                orderLine.setProduct(product);
                orderLine.setQuantity(ol.getQuantity());
                BigDecimal orderLineTotal = product.getPrice().multiply(new BigDecimal(ol.getQuantity()));
                orderLine.setPrice(orderLineTotal);
                totalPrice = totalPrice.add(orderLineTotal);
                orderLine.setStoreOrder(order);
                orderLines.add(orderLine);
            }
        }
        order.setPriceTotal(totalPrice);
        order.setLines(orderLines);
        return storeOrderRepository.save(order);
    }

    @Override
    public List<OrderResponse> getUserOrders() {
        List<OrderResponse> orders = new ArrayList<>();
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        Optional<StoreUser> storeUser = storeUserRepository.findByUsername(principal.getName());
        storeUser.ifPresent(user -> storeOrderRepository.findAllByStoreUserIdOrderByOrderDateDesc(user.getId()).forEach(o -> orders.add(getOrderResponse(o, OrderResponse.class))));
        return orders;
    }

    @Override
    public OrderDetailsResponse getOrderDetails(Long orderId) {
        OrderDetailsResponse detailsResponse = new OrderDetailsResponse();
        Optional<StoreOrder> storeOrder = storeOrderRepository.findById(orderId);
        if (storeOrder.isPresent()) {
            detailsResponse = getOrderResponse(storeOrder.get(), OrderDetailsResponse.class);
            detailsResponse.setOrderLines(new ArrayList<>());
            for (OrderLine ol : storeOrder.get().getLines()) {
                OrderLineResponse orderLine = new OrderLineResponse();
                orderLine.setPrice(ol.getPrice());
                orderLine.setProduct(modelMapper.map(ol.getProduct(), ProductItem.class));
                orderLine.getProduct().setPhotos(Collections.singletonList(Base64.getEncoder().encodeToString(ol.getProduct().getPhotos().get(0).getPhoto())));
                orderLine.setQuantity(ol.getQuantity());
                detailsResponse.getOrderLines().add(orderLine);
            }
        }
        return detailsResponse;
    }

    @Override
    public List<StoreOrderStatus> getStatuses() {
        return storeOrderStatusRepository.findAll();
    }

    private <T extends OrderResponse> T getOrderResponse(StoreOrder o, Class<T> type) {
        T order = null;
        try {
            order = type.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException |
                 NoSuchMethodException e) {
            log.error(e);
        }
        order.setId(o.getId());
        order.setOrderDate(o.getOrderDate());
        order.setOrderStatus(o.getOrderStatus());
        order.setPriceTotal(o.getPriceTotal());
        return type.cast(order);
    }
}
