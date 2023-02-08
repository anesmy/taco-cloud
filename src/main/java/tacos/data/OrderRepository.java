package tacos.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import tacos.models.TacoOrder;
import tacos.models.User;

import java.util.List;

public interface OrderRepository
        extends CrudRepository<TacoOrder, Long> {
    TacoOrder save(TacoOrder order);

    List<TacoOrder> findByUserOrderByPlacedAtDesc(User user,  Pageable pageable);
}
