import lombok.*;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@EqualsAndHashCode(exclude = {"placedAt"})
@With
@Data
public class Order {
    final @NonNull String id;
    final @NonNull List<Product> products;
    final @NonNull OrderStatus status;
    final @NonNull Instant placedAt;

    public Order(String id, List<Product> products) {
        this(id, products, OrderStatus.PROCESSING, Instant.now());
    }

    public Order(String id, List<Product> products, Instant placedAt) {
        this(id, products, OrderStatus.PROCESSING, placedAt);
    }

    public Order(String id, List<Product> products, OrderStatus orderStatus) {
        this(id, products, orderStatus, Instant.now());
    }
}
