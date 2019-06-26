package mari.hans.movie_information.Service;

import mari.hans.movie_information.Domain.MovieDetail;
import mari.hans.movie_information.Domain.MovieInformation;

import java.util.List;

public interface CrawlingService {

    List<MovieInformation> infoadd();

    MovieInformation infoSearch(Long id);

    MovieInformation infouniqueSearch(String uid);

    Boolean infodelete();

    Long infoCount();

    MovieInformation infoSearchfirstid();

}
