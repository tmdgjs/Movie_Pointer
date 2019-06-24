package mari.hans.movie_information.Service;

import mari.hans.movie_information.DAO.MovieRepository;
import mari.hans.movie_information.Domain.MovieDetail;
import mari.hans.movie_information.Domain.MovieInformation;

import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import org.json.simple.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CrawlingServiceImpl implements CrawlingService {

    @Autowired
    private MovieRepository movierepo;

    public static Date dayparse(String day) { //date 변환
        day = day.substring(0,8);
        day = day.replace(".", "-");

            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(day);
                return date;
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }


    }

    public static String UniqueNumber_Parser(Elements element, int number) { //영화 번호
        String uniquen = element.get(number).absUrl("href");

        int idx= uniquen.indexOf("=");

        String afteruniquen = uniquen.substring(idx+1);

        return afteruniquen;
    }

    public static JSONObject getJson(MovieInformation mv) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("title", mv.getMovie_name());
        jsonObject.put("src", mv.getMovie_imagehref());
        jsonObject.put("description", mv.getMovie_info());
        jsonObject.put("day", mv.getMovie_startday());

        return jsonObject;
    }


    @Override
    public List<MovieInformation> infoadd() {
        JSONArray movieArray = new JSONArray();

        for(int count = 1 ; count<5 ; count++){
            List<MovieInformation> movies = new ArrayList<>();
            String url = "https://movie.daum.net/premovie/released?opt=reserve&page="+count;

            try{
                Document doc = Jsoup.connect(url).get();

                Elements titles = doc.select("a.name_movie");

                Elements descri = doc.select("a.desc_movie");

                Elements img = doc.select("img[class=img_g]");

                Elements startday = doc.select("span.info_state");

                for (int i = 0; i < titles.size(); i++) {
                    String title = titles.get(i).text();
                    String des = descri.get(i).text();
                    String image = img.get(i).attr("abs:src");
                    String day = startday.get(i).text();

                    String uniques = UniqueNumber_Parser(titles,i);

                    MovieInformation mv = new MovieInformation(title, des, image, dayparse(day),UniqueNumber_Parser(titles,i));



                    System.out.print(mv);
                    movies.add(mv);





                }

                this.movierepo.saveAll(movies);

            }
            catch(IOException e){
                return null;
            }
        }


        return this.movierepo.findAll();
    }

    @Override
    public List<MovieDetail> d_infoadd() {


        return null;
    }

    @Override
    public MovieInformation infoSearch(Long id) {
        Optional<MovieInformation> mvls = this.movierepo.findById(id);

        return mvls.get();

    }

    @Override
    public MovieInformation infoSearch1(String uid) {
        Optional<MovieInformation> mvls = this.movierepo.findByMovieunique(uid);

         return mvls.get();


    }

    @Override
    public MovieInformation infoSearch_nametouniq(String name) {
        return null;
    }


    @Override
    public Boolean infodelete() {
        try{
            this.movierepo.deleteAll();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    @Override
    public Long infoCount() {
        return this.movierepo.count();
    }
}
