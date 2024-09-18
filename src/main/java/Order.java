import lombok.*;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@EqualsAndHashCode(exclude = {"placedAt"})
@With
@Builder
@Data
public class Order {
    final @NonNull String id;
    final @NonNull List<Product> products;
    @Builder.Default
    final @NonNull OrderStatus status = OrderStatus.PROCESSING;
    @Builder.Default
    final @NonNull Instant placedAt = Instant.now();
}
