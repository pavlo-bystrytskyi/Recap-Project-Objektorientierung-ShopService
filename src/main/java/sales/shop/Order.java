package sales.shop;

import lombok.*;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@EqualsAndHashCode(exclude = {"placedAt"})
@With
@Builder
@Data
public class Order {
    final private @NonNull String id;
    final private @NonNull List<Product> products;
    @Builder.Default
    final private @NonNull OrderStatus status = OrderStatus.PROCESSING;
    @Builder.Default
    final private @NonNull Instant placedAt = Instant.now();
}
