package mari.hans.movie_information.DAO;

import mari.hans.movie_information.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    public Optional<User> findByUseridAndPassword(String userid, String password);

    Optional<User> findByUserid(String userid);
}
