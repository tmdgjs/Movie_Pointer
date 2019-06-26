package mari.hans.movie_information.DAO;

import mari.hans.movie_information.Domain.MovieActor;
import mari.hans.movie_information.Domain.MovieInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<MovieInformation, Long> {

    public Optional<MovieInformation> findByMovieunique(String movie_unique);

    List<MovieInformation> findByIdOrderById(Long id);


}
