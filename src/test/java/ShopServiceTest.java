import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    @Test
    void addOrderTest() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        Order expected = new Order("-1", List.of(new Product("1", "Apfel")));
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getProducts(), actual.getProducts());
        assertNotNull(expected.getId());
    }

    @Test
    void addOrderTest_defaultStatus() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        assertEquals(OrderStatus.PROCESSING, actual.getStatus());
    }

    @Test
    void addOrderTest_whenInvalidProductId_expectNull() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1", "2");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        assertNull(actual);
    }

    @Test
    void getOrdersByStatus_ordersExist() {
        //GIVEN
        ShopService shopService = new ShopService();
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
        ShopService shopService = new ShopService();
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
        ShopService shopService = new ShopService();

        //WHEN

        //THEN
        List<Order> actual = shopService.getOrdersByStatus(OrderStatus.PROCESSING);
        assertEquals(0, actual.size());
    }
}
