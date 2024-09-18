import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    @Test
    void addOrderTest() {
        //GIVEN
        ShopService shopService = new ShopService(new ProductRepo(), new OrderMapRepo());
        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        Order expected = Order.builder().id("-1").products(List.of(new Product("1", "Apfel"))).build();
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getProducts(), actual.getProducts());
        assertNotNull(expected.getId());
    }

    @Test
    void addOrderTest_defaultStatus() {
        //GIVEN
        ShopService shopService = new ShopService(new ProductRepo(), new OrderMapRepo());
        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        assertEquals(OrderStatus.PROCESSING, actual.getStatus());
    }

    @Test
    void addOrderTest_whenInvalidProductId_expectException() {
        //GIVEN
        ShopService shopService = new ShopService(new ProductRepo(), new OrderMapRepo());
        List<String> productsIds = List.of("1", "3");

        //WHEN

        //THEN
        assertThrowsExactly(
                NoSuchProductException.class,
                () -> shopService.addOrder(productsIds)
        );
    }

    @Test
    void updateOrderTest_existentOrder() {
        //GIVEN
        ShopService shopService = new ShopService(new ProductRepo(), new OrderMapRepo());
        List<String> productsIds = List.of("1", "2");
        Order order = shopService.addOrder(productsIds);

        //WHEN
        Order newOrder = shopService.updateOrder(order.getId(), OrderStatus.IN_DELIVERY);

        //THEN
        assertEquals(OrderStatus.IN_DELIVERY, newOrder.getStatus());
        assertEquals(order.getId(), newOrder.getId());
        assertEquals(order.getProducts(), newOrder.getProducts());
    }

    @Test
    void updateOrderTest_orderReplaced() {
        //GIVEN
        ShopService shopService = new ShopService(new ProductRepo(), new OrderMapRepo());
        List<String> productsIds = List.of("1", "2");
        Order order = shopService.addOrder(productsIds);

        //WHEN
        Order expected = shopService.updateOrder(order.getId(), OrderStatus.IN_DELIVERY);

        //THEN
        Order actual = shopService.getOrder(order.getId());
        assertEquals(expected, actual);
    }

    @Test
    void updateOrderTest_notExistentOrder() {
        //GIVEN
        ShopService shopService = new ShopService(new ProductRepo(), new OrderMapRepo());
        List<String> productsIds = List.of("1", "2");
        shopService.addOrder(productsIds);

        //WHEN

        //THEN
        assertThrowsExactly(
                NoSuchOrderException.class,
                () -> shopService.updateOrder("-1", OrderStatus.IN_DELIVERY)
        );
    }

    @Test
    void getOrdersByStatus_ordersExist() {
        //GIVEN
        ShopService shopService = new ShopService(new ProductRepo(), new OrderMapRepo());
        List<String> productsIds = List.of("1");

        //WHEN
        shopService.addOrder(productsIds);
        shopService.addOrder(productsIds);

        //THEN
        List<Order> actual = shopService.getOrdersByStatus(OrderStatus.PROCESSING);
        assertEquals(2, actual.size());
    }

    @Test
    void getOrdersByStatus_ordersWithSuchStatusExist() {
        //GIVEN
        ShopService shopService = new ShopService(new ProductRepo(), new OrderMapRepo());
        List<String> productsIds = List.of("1");

        //WHEN
        shopService.addOrder(productsIds);
        shopService.addOrder(productsIds);

        //THEN
        List<Order> actual = shopService.getOrdersByStatus(OrderStatus.IN_DELIVERY);
        assertEquals(0, actual.size());
    }


    @Test
    void getOrdersByStatus_noOrdersExist() {
        //GIVEN
        ShopService shopService = new ShopService(new ProductRepo(), new OrderMapRepo());

        //WHEN

        //THEN
        List<Order> actual = shopService.getOrdersByStatus(OrderStatus.PROCESSING);
        assertEquals(0, actual.size());
    }
}
