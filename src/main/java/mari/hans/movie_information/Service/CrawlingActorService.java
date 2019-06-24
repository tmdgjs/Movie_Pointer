package mari.hans.movie_information.Service;

import mari.hans.movie_information.Domain.MovieActor;
import mari.hans.movie_information.Domain.MovieDetail;

import java.util.List;
import java.util.Optional;

public interface CrawlingActorService {

    List<MovieActor> actoradd();

    //Optional<MovieActor> movie_actorsearch(String uniques, String role);

    List<MovieActor> actorsearch(String uid);

}
