import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderListRepoTest {

    @Test
    void getOrders() {
        //GIVEN
        OrderListRepo repo = new OrderListRepo();

        Product product = new Product("1", "Apfel");
        Order newOrder = Order.builder().id("1").products(List.of(product)).build();
        repo.addOrder(newOrder);

        //WHEN
        List<Order> actual = repo.getOrders();

        //THEN
        List<Order> expected = new ArrayList<>();
        Product product1 = new Product("1", "Apfel");
        expected.add(Order.builder().id("1").products(List.of(product1)).build());

        assertEquals(actual, expected);
    }

    @Test
    void getOrderById() {
        //GIVEN
        OrderListRepo repo = new OrderListRepo();

        Product product = new Product("1", "Apfel");
        Order newOrder = Order.builder().id("1").products(List.of(product)).build();;
        repo.addOrder(newOrder);

        //WHEN
        Order actual = repo.getOrderById("1").get();

        //THEN
        Product product1 = new Product("1", "Apfel");
        Order expected = Order.builder().id("1").products(List.of(product1)).build();

        assertEquals(actual, expected);
    }

    @Test
    void addOrder() {
        //GIVEN
        OrderListRepo repo = new OrderListRepo();
        Product product = new Product("1", "Apfel");
        Order newOrder = Order.builder().id("1").products(List.of(product)).build();

        //WHEN
        Order actual = repo.addOrder(newOrder);

        //THEN
        Product product1 = new Product("1", "Apfel");
        Order expected = Order.builder().id("1").products(List.of(product1)).build();
        assertEquals(actual, expected);
        assertEquals(repo.getOrderById("1").get(), expected);
    }

    @Test
    void removeOrder() {
        //GIVEN
        OrderListRepo repo = new OrderListRepo();

        //WHEN
        repo.removeOrder("1");

        //THEN
        assertTrue(repo.getOrderById("1").isEmpty());
    }
}
