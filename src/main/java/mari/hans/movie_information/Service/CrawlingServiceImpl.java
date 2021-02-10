package mari.hans.movie_information.Service;

import mari.hans.movie_information.DAO.MovieRepository;

import mari.hans.movie_information.Domain.MovieInformation;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CrawlingServiceImpl implements CrawlingService {

    @Autowired
    private MovieRepository movieRepository;

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


    @Override
    public List<MovieInformation> infoadd() { //기본정보 추가

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

                    MovieInformation mv = new MovieInformation(title, des, image, dayparse(day),UniqueNumber_Parser(titles,i));

                    movies.add(mv);


                }

                this.movieRepository.saveAll(movies);

            }
            catch(IOException e){
                return null;
            }
        }
        return this.movieRepository.findAll();
    }


    @Override
    public MovieInformation infoSearch(Long id) { //id 값으로 검색
        MovieInformation mvls = this.movieRepository.findById(id).orElse(null);

        return mvls;

    }

    @Override
    public MovieInformation infouniqueSearch(String uid) { //영화 번호로 검색
        Optional<MovieInformation> mvls = this.movieRepository.findByMovieunique(uid);

         return mvls.get();

    }



    @Override
    public Boolean infodelete() { //기본정보 삭제
        try{
            this.movieRepository.deleteAll();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    @Override
    public Long infoCount() {
        return this.movieRepository.count();
    } // 영화 갯수

    @Override
    public MovieInformation infoSearchfirstid() { //첫번째 영화 id
        List<MovieInformation> mvls = this.movieRepository.findAll();
        return mvls.get(0);
    }
}
