import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ShopService {
    private ProductRepo productRepo = new ProductRepo();
    private OrderRepo orderRepo = new OrderMapRepo();

    public Order addOrder(List<String> productIds) {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Optional<Product> productToOrder = productRepo.getProductById(productId);
            if (productToOrder.isEmpty()) {
                throw new NoSuchProductException("Product mit der Id: " + productId + " konnte nicht bestellt werden!");
            }
            products.add(productToOrder.get());
        }

        Order newOrder = new Order(UUID.randomUUID().toString(), products, OrderStatus.PROCESSING, Instant.now());

        return orderRepo.addOrder(newOrder);
    }

    public Order updateOrder(String orderId, OrderStatus newStatus) {
        Optional<Order> order = orderRepo.getOrderById(orderId);
        if (order.isEmpty()) {
            throw new NoSuchOrderException("Order with id: " + orderId + " not found.");
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
            throw new NoSuchOrderException("Order with id: " + orderId + " not found.");
        }

        return order.get();
    }
}
