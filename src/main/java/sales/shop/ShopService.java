package sales.shop;

import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ShopService {
    private final ProductRepo productRepo;
    private final OrderRepo orderRepo;
    private final IdService idService;

    public Order addOrder(List<String> productIds) {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Optional<Product> productToOrder = productRepo.getProductById(productId);
            if (productToOrder.isEmpty()) {
                throw new NoSuchProductException("sales.shop.Product mit der Id: " + productId + " konnte nicht bestellt werden!");
            }
            products.add(productToOrder.get());
        }

        Order newOrder = new Order(idService.generateId(), products, OrderStatus.PROCESSING, Instant.now());

        return orderRepo.addOrder(newOrder);
    }

    public Order updateOrder(String orderId, OrderStatus newStatus) {
        Optional<Order> order = orderRepo.getOrderById(orderId);
        if (order.isEmpty()) {
            throw new NoSuchOrderException("sales.shop.Order with id: " + orderId + " not found.");
        }

        Order newOrder = order.get().withStatus(newStatus);
        orderRepo.removeOrder(orderId);
        orderRepo.addOrder(newOrder);

        return newOrder;
    }

    public List<Order> getOrdersByStatus(OrderStatus orderStatus) {
        return orderRepo.getOrders().stream().filter(
                order -> order.getStatus() == orderStatus
        ).toList();
    }

    public Order getOrder(String orderId) {
        Optional<Order> order = orderRepo.getOrderById(orderId);
        if (order.isEmpty()) {
            throw new NoSuchOrderException("sales.shop.Order with id: " + orderId + " not found.");
        }

        return order.get();
    }

    public List<Order> getOrders() {
        return orderRepo.getOrders();
    }

    public Map<OrderStatus, Order> getOldestOrderPerStatus() {
        return orderRepo
                .getOrders()
                .stream()
                .collect(
                        Collectors.groupingBy(
                                Order::getStatus,
                                Collectors.collectingAndThen(
                                        Collectors.minBy(Comparator.comparing(Order::getPlacedAt)),
                                        optionalOrder -> optionalOrder.orElse(null)
                                )
                        )
                );
    }
}
