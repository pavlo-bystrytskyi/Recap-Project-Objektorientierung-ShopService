package sales.shop;

import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepoTest {

    @org.junit.jupiter.api.Test
    void getProducts() {
        //GIVEN
        ProductRepo repo = new ProductRepo();

        //WHEN
        List<Product> actual = repo.getProducts();

        //THEN
        List<Product> expected = new ArrayList<>();
        expected.add(new Product("1", "Apfel"));
        expected.add(new Product("2", "Birne"));
        assertEquals(actual, expected);
    }

    @org.junit.jupiter.api.Test
    void getProductById() {
        //GIVEN
        ProductRepo repo = new ProductRepo();

        //WHEN
        Product actual = repo.getProductById("1").get();

        //THEN
        Product expected = new Product("1", "Apfel");
        Assertions.assertEquals(actual, expected);
    }

    @org.junit.jupiter.api.Test
    void addProduct() {
        //GIVEN
        ProductRepo repo = new ProductRepo();
        Product newProduct = new Product("3", "Banane");

        //WHEN
        Product actual = repo.addProduct(newProduct);

        //THEN
        Product expected = new Product("3", "Banane");
        Assertions.assertEquals(actual, expected);
        Assertions.assertEquals(repo.getProductById("3").get(), expected);
    }

    @org.junit.jupiter.api.Test
    void removeProduct() {
        //GIVEN
        ProductRepo repo = new ProductRepo();

        //WHEN
        repo.removeProduct("1");

        //THEN
        Assertions.assertTrue(repo.getProductById("1").isEmpty());
    }
}
