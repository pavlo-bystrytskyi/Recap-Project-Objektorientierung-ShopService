package sales.transactions;

import sales.shop.OrderStatus;
import sales.shop.ShopService;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.*;

public class TransactionsReader {
    private final ShopService shopService;
    private final Map<String, String> orderAliasMap = new HashMap<>();

    public TransactionsReader(ShopService shopService) {
        this.shopService = shopService;
    }

    private void processFile(Path path) throws FileNotFoundException {
        Scanner scanner = new Scanner(path.toFile());
        while (scanner.hasNextLine()) {
            this.processNextLine(scanner);
        }
    }

    private void processNextLine(Scanner scanner) {
        String line = scanner.nextLine();
        String[] arguments = line.split(" ");
        if (arguments.length == 0) {
            throw new IllegalStateException("Empty line");
        }
        Operation operation = null;
        try {
            operation = Operation.valueOf(arguments[0]);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Unknown operation: " + arguments[0]);
        }
        String[] methodArguments = Arrays.copyOfRange(arguments, 1, arguments.length);
        switch (operation) {
            case addOrder:
                this.addOrder(methodArguments);
                break;
            case setStatus:
                this.setStatus(methodArguments);
                break;
            case printOrders:
                this.printOrders(methodArguments);
                break;
        }
    }

    private void printOrders(String[] methodArguments) {
        if (methodArguments.length != 0) {
            throw new IllegalStateException("Incorrect number of arguments");
        }

        System.out.println(shopService.getOrders());
    }

    private void setStatus(String[] methodArguments) {
        if (methodArguments.length != 2) {
            throw new IllegalStateException("Incorrect number of arguments");
        }
        String orderAlias = methodArguments[0];
        OrderStatus status = OrderStatus.valueOf(methodArguments[1]);
        // TODO: check if it's possible.
        if (status == null) {
            throw new IllegalStateException("Incorrect order status");
        }
        String orderId = orderAliasMap.get(orderAlias);
        if (orderId == null) {
            throw new IllegalStateException("Order alias not known");
        }
        shopService.updateOrder(orderId, status);
    }

    private void addOrder(String[] methodArguments) {
        if (methodArguments.length < 2) {
            throw new IllegalStateException("Incorrect number of arguments");
        }
        String orderAlias = methodArguments[0];
        String[] arguments = Arrays.copyOfRange(methodArguments, 1, methodArguments.length);
        String orderId = shopService.addOrder(List.of(arguments)).getId();
        orderAliasMap.put(orderAlias, orderId);
    }

    public void readFile(Path path) throws FileNotFoundException {
        if (!path.toFile().exists()) {
            throw new FileNotFoundException();
        }
        this.orderAliasMap.clear();
        this.processFile(path);
    }

    private enum Operation {
        addOrder,
        setStatus,
        printOrders;
    }
}
