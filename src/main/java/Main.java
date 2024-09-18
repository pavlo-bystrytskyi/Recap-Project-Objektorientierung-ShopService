import java.util.List;

public class Main {
    public static void main(String[] args) {
        ProductRepo productRepo = new ProductRepo();
        OrderRepo orderRepo = new OrderMapRepo();
        IdService idService = new IdService();
        ShopService shopService = new ShopService(productRepo, orderRepo, idService);

        Order firstOrder = shopService.addOrder(List.of("1"));
        Order secondOrder = shopService.addOrder(List.of("2"));
        Order thirdOrder = shopService.addOrder(List.of("1", "2"));

        shopService.updateOrder(firstOrder.getId(), OrderStatus.IN_DELIVERY);
        System.out.println(shopService.getOldestOrderPerStatus());
    }
}
