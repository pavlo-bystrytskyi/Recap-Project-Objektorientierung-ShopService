import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.With;

import java.util.List;

@AllArgsConstructor
@With
@Data
public class Order {
        final @NonNull String id;
        final @NonNull List<Product> products;
        @NonNull OrderStatus status;

        public Order(String id, List<Product> products) {
            this(id, products, OrderStatus.PROCESSING);
        }
}
