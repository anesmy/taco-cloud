package tacos.data;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import tacos.models.Taco;

public interface TacoRepository extends CrudRepository<Taco, Long> {

    Iterable<Taco> findAll(Pageable pageable);
}
