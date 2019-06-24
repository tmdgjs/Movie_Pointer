package mari.hans.movie_information.DAO;

import mari.hans.movie_information.Domain.MovieImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieImageRepository extends JpaRepository<MovieImage,Long> {

    Optional<MovieImage> findByMovieunique(String uid);
}
