package mari.hans.movie_information.Service;

import mari.hans.movie_information.Domain.MovieDetail;

import java.util.List;

public interface CrawlingDetailService {

    List<MovieDetail> detailadd();

    MovieDetail detailSearch(String uid);

    Boolean detaildelete();

}
