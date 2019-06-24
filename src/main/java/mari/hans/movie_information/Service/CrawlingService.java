package mari.hans.movie_information.Service;

import mari.hans.movie_information.Domain.MovieDetail;
import mari.hans.movie_information.Domain.MovieInformation;

import java.util.List;

public interface CrawlingService {

    List<MovieInformation> infoadd();

    List<MovieDetail> d_infoadd();

    MovieInformation infoSearch(Long id);

    MovieInformation infoSearch1(String uid);

    MovieInformation infoSearch_nametouniq(String name);

    Boolean infodelete();

    Long infoCount();

}
