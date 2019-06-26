package mari.hans.movie_information.Service;

import mari.hans.movie_information.Domain.MovieActor;

import java.util.List;

public interface CrawlingActorService {

    List<MovieActor> actoradd();

    Boolean actordelete();

    List<MovieActor> actorsearch(String uid);

}
