package mari.hans.movie_information.Service;

import mari.hans.movie_information.DAO.MovieActorRepostiory;
import mari.hans.movie_information.DAO.MovieDetailRepository;
import mari.hans.movie_information.Domain.MovieActor;
import mari.hans.movie_information.Domain.MovieDetail;
import mari.hans.movie_information.Domain.MovieInformation;
import org.json.simple.JSONArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CrawlingActorServiceImpl implements CrawlingActorService {

    @Autowired
    private MovieActorRepostiory movieActorRepostiory;

    public static String UniqueNumber_Parser(Elements element, int number) { //영화 번호
        String uniquen = element.get(number).absUrl("href");

        int idx= uniquen.indexOf("=");

        String afteruniquen = uniquen.substring(idx+1);

        return afteruniquen;
    }

    public static List<MovieActor> movie_actor(Elements element, int number)throws IOException{ //배우 가져오기
        String a_url = "https://movie.daum.net/moviedb/crew?movieId="+UniqueNumber_Parser(element, number);
        Document doc = Jsoup.connect(a_url).get();

        Elements actor_link = doc.select("li > a > span > img");
        Elements actor_job = doc.select("span.txt_awards > span");

        ArrayList<MovieActor> mals = new ArrayList<MovieActor>();


        for(int i =0; i <actor_link.size();i++) {

            String actor = actor_link.get(i).attr("alt");

            String actor_img = actor_link.get(i).attr("src");

            String actor_jobs = actor_job.get(i).text();


            if(actor_jobs.equals("")) {
                if(i < 3 ){
                    actor_jobs = "감독";
                }else{
                    actor_jobs = "역할 없음";
                }

            }

            MovieActor ma = new MovieActor(actor,actor_jobs,actor_img,UniqueNumber_Parser(element, number));
            mals.add(ma);
        }

       return mals;


    }

    @Override
    public List<MovieActor> actoradd() { //배우 추가


        for (int count = 1; count < 5; count++) {

            List<MovieActor> movies_a = new ArrayList<>();
            String a_url = "https://movie.daum.net/premovie/released?opt=reserve&page="+count;
            try {
                Document doc = Jsoup.connect(a_url).get();

                Elements titles = doc.select("a.name_movie");


                for (int i = 0; i < titles.size(); i++) {


                    for (int j = 0; j < movie_actor(titles, i).size(); j++) {
                        movies_a.add(movie_actor(titles, i).get(j));
                    }

                }

               this.movieActorRepostiory.saveAll(movies_a);

            } catch (IOException e) {
                return null;
            }

        }
        return  movieActorRepostiory.findAll();
    }

    @Override
    public Boolean actordelete() { //배우 삭제
        try{
            this.movieActorRepostiory.deleteAll();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }


    @Override
    public List<MovieActor> actorsearch(String uid) { //배우 검색

        return this.movieActorRepostiory.findByMovieunique(uid);

    }

}
