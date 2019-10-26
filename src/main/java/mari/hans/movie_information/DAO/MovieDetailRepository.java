package mari.hans.movie_information.DAO;

import mari.hans.movie_information.Domain.MovieDetail;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MovieDetailRepository extends JpaRepository<MovieDetail,Long> {

    public Optional<MovieDetail> findByMovieunique(String movie_unique);
}
