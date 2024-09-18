import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@AllArgsConstructor
@Data
public class Order {
        final @NonNull String id;
        final @NonNull List<Product> products;
        @NonNull OrderStatus status;

        public Order(String id, List<Product> products) {
            this(id, products, OrderStatus.PROCESSING);
        }
}
