import java.util.List;

public class Main {
    public static void main(String[] args) {
        ProductRepo productRepo = new ProductRepo();
        OrderRepo orderRepo = new OrderMapRepo();
        ShopService shopService = new ShopService(productRepo, orderRepo);

        shopService.addOrder(List.of("1"));
        shopService.addOrder(List.of("2"));
        shopService.addOrder(List.of("1", "2"));
    }
}
