package mari.hans.movie_information.Service;

import mari.hans.movie_information.DAO.MovieDetailRepository;
import mari.hans.movie_information.DAO.MovieImageRepository;
import mari.hans.movie_information.Domain.MovieDetail;
import mari.hans.movie_information.Domain.MovieImage;
import org.json.simple.JSONArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CrawlingImageServiceImpl implements CrawlingImageService{

    @Autowired
    private MovieImageRepository movieImageRepository;

    public static String UniqueNumber_Parser(Elements element, int number) { //영화 번호
        String uniquen = element.get(number).absUrl("href");

        int idx= uniquen.indexOf("=");

        String afteruniquen = uniquen.substring(idx+1);

        return afteruniquen;
    }

        public static MovieImage imageinfo(Elements element, int number,String title) throws IOException {
            try{
                ArrayList<String> image_ls = new ArrayList<String>();

                String urls = "http://search.zum.com/search.zum?method=uni&option=accu&rd=1&qm=f_typing.top&query=영화 "+title;

                Document doc = Jsoup.connect(urls).get();

                Elements image_url_tag = doc.select("li.preview > a");

                String images_url = image_url_tag.get(1).attr("href");

                Document doc2 = Jsoup.connect(images_url).get();

                Elements image_tag = doc2.select("div.phslul > ul > li > a");

                System.out.println(images_url);

                for(int i = 0 ; i< 5 ; i++) {
                    try {
                        String image = image_tag.get(i).attr("data-image");
                        image_ls.add(image);

                    } catch (IndexOutOfBoundsException e) {
                        image_ls.add(null);
                    }
                }

                MovieImage mi = new MovieImage(UniqueNumber_Parser(element, number),image_ls.get(0),image_ls.get(1),image_ls.get(2),image_ls.get(3),image_ls.get(4));

                return mi;
            }catch(IndexOutOfBoundsException e){
                MovieImage error_mi = new MovieImage(UniqueNumber_Parser(element, number),null,null,null,null,null);

                return error_mi;
            }



        }


    @Override
    public List<MovieImage> imageadd() {
        JSONArray movieArray = new JSONArray();

        for (int count = 1; count < 5; count++) {

            List<MovieImage> movies_i = new ArrayList<>();
            String main_url = "https://movie.daum.net/premovie/released?opt=reserve&page="+count;

            try {
                Document doc = Jsoup.connect(main_url).get();

                Elements titles = doc.select("a.name_movie");


                for (int i = 0; i < titles.size(); i++) {
                    String title = titles.get(i).text();


                    movies_i.add(imageinfo(titles, i, title));
                }

                this.movieImageRepository.saveAll(movies_i);

            } catch (IOException e) {
                return null;
            }
        }
        return movieImageRepository.findAll();
    }

    @Override
    public MovieImage imagesearch(String uid) {

        Optional<MovieImage> imagels = this.movieImageRepository.findByMovieunique(uid);
        return imagels.get();

    }

}
