package org.example;

import sales.shop.*;
import sales.transactions.TransactionsReader;

import java.io.FileNotFoundException;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        ProductRepo productRepo = new ProductRepo();
        productRepo.addProduct(new Product("1", "Äpfel"));
        productRepo.addProduct(new Product("2", "Birne"));
        productRepo.addProduct(new Product("3", "Banan"));
        productRepo.addProduct(new Product("4", "Erdbeere"));
        OrderRepo orderRepo = new OrderMapRepo();
        IdService idService = new IdService();
        ShopService shopService = new ShopService(productRepo, orderRepo, idService);

        TransactionsReader transactionsReader = new TransactionsReader(shopService);
        transactionsReader.readFile(Path.of("transactions.txt"));
    }
}
