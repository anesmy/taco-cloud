package tacos.data;

import org.springframework.data.repository.CrudRepository;
import tacos.models.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
}
