package tacos.data;

import tacos.models.TacoOrder;
import java.util.Optional;

public interface OrderRepository {
    TacoOrder save(TacoOrder order);
}
