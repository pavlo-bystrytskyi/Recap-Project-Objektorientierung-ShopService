package sales.shop;

import java.util.UUID;

public class IdService {
    public String generateId() {
        return UUID.randomUUID().toString();
    }
}
