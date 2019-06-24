package mari.hans.movie_information.DAO;

import mari.hans.movie_information.Domain.MovieActor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieActorRepostiory extends JpaRepository<MovieActor, Long> {

    //Optional<MovieActor> findByMovie_uniqueAndActor_role(String movie_unique,String actor_role);
    //Optional<MovieActor> findByMovieunique(String movieunique);

    List<MovieActor> findByMovieunique(String movieunique);
}
