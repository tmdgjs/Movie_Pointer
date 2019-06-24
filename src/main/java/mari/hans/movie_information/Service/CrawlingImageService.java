package mari.hans.movie_information.Service;

import mari.hans.movie_information.Domain.MovieImage;

import java.util.List;

public interface CrawlingImageService {

    List<MovieImage> imageadd();

    MovieImage imagesearch(String uid);

}
