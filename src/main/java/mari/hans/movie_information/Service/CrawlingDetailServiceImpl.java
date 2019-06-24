package mari.hans.movie_information.Service;

import mari.hans.movie_information.DAO.MovieDetailRepository;
import mari.hans.movie_information.Domain.MovieDetail;
import mari.hans.movie_information.Domain.MovieInformation;
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
public class CrawlingDetailServiceImpl implements CrawlingDetailService {

    @Autowired
    private MovieDetailRepository movieDetailRepository;

    public static String UniqueNumber_Parser(Elements element, int number) { //영화 번호
        String uniquen = element.get(number).absUrl("href");

        int idx= uniquen.indexOf("=");

        String afteruniquen = uniquen.substring(idx+1);

        return afteruniquen;
    }

    public static String countryname(ArrayList<String> c_lst,String data[]) {

        for(int i = 0; i <data.length;i++) { //country function
            if(data[i].contains(".")) {

                for(int j = 1 ; j<i;j++) {
                    if(data[j].contains(",")) {
                        data[j] = data[j].replace(",","");
                        c_lst.add(data[j]);

                    }
                    else {
                        c_lst.add(data[j]);
                    }

                }
                break;

            }

        }

        String country_str = "";
        for(int i = 0 ; i<c_lst.size();i++) {
            if(i >= 1) {
                country_str = country_str + ", ";
            }
            country_str = country_str + c_lst.get(i);
        }

        return country_str;

    }

    public static String attendancenum(String title) throws IOException {
        ArrayList<String> person = new ArrayList<String>();

        String urls = "https://search.daum.net/search?w=tot&DA=YZR&t__nil_searchbox=btn&sug=&sugo=&q=영화 "+title;

        Document doc = Jsoup.connect(urls).get();

        Elements numbers = doc.select("em.f_red");

        String nums = numbers.text();

        String data[] = nums.split(" ");

        //person.add(data[data.length-2]);

        if(data[0].equals("")){
            return "0명";
        }

        //return data[data.length-2];
        return data[0] + "명";

    }

    public static List<String> movie_image_zum_search(String title) throws IOException{

        ArrayList<String> image_ls = new ArrayList<String>();

        String urls = "http://search.zum.com/search.zum?method=uni&option=accu&rd=1&qm=f_typing.top&query=영화 "+title;

        Document doc = Jsoup.connect(urls).get();

        Elements image_url_tag = doc.select("li.preview > a");

        String images_url = image_url_tag.get(1).attr("href");

        Document doc2 = Jsoup.connect(images_url).get();

        Elements image_tag = doc2.select("div.phslul > ul > li > a");

        //System.out.println(image_tag);
        try {
            for(int i = 0 ; i< 5 ; i++) {

                String image = image_tag.get(i).attr("data-image");
                image_ls.add(image);
            }
        }
        catch(IndexOutOfBoundsException e) {
            System.out.println("이미지가 없습니다.");
        }
        return image_ls;



    }


    public static MovieDetail detailinfo(Elements element, int number) throws IOException {
        JSONArray d_movieArray = new JSONArray();

        ArrayList<String> detail_lst = new ArrayList<String>();

        ArrayList<String> country_lst = new ArrayList<String>();


        String d_url = "https://movie.daum.net/moviedb/main?movieId="+UniqueNumber_Parser(element,number);

        Document doc = Jsoup.connect(d_url).get();

        Elements titles = doc.select("dd");

        Elements mtitles = doc.select("h2.tit_rel");

        String title = titles.text();

        String mtitle = mtitles.text();

        String data[] = title.split(" ");

        detail_lst.add(data[0]);

        for(int i = 0; i <data.length;i++) {

            if(data[i].equals("(감독)") == true) {

                if(data[data.length-1].contains("위")) {
                    detail_lst.add(data[data.length-1]);
                }
                else {
                    detail_lst.add("정보없음");
                }

                detail_lst.add(data[i-2].replace(",",""));
                detail_lst.add(data[i-1]);

                MovieDetail md = new MovieDetail(detail_lst.get(1),detail_lst.get(0),countryname(country_lst,data),detail_lst.get(2),detail_lst.get(3),attendancenum(mtitle),UniqueNumber_Parser(element,number));
                //System.out.println(title);
                return md;



            }
        }
        return null;
    }

    @Override
    public List<MovieDetail> detailadd() {
        JSONArray movieArray = new JSONArray();
        for (int count = 1; count < 5; count++) {

            List<MovieDetail> movies_d = new ArrayList<>();
            String d_url = "https://movie.daum.net/premovie/released?opt=reserve&page="+count;

            try {
                Document doc = Jsoup.connect(d_url).get();

                Elements titles = doc.select("a.name_movie");


                for (int i = 0; i < titles.size(); i++) {


                    //detailinfo(titles,i);

                    //movieArray.add(getJson(mv));
                    movies_d.add(detailinfo(titles, i));
                }

                 this.movieDetailRepository.saveAll(movies_d);

            } catch (IOException e) {
                return null;
            }

        }
        return movieDetailRepository.findAll();
    }

    @Override
    public MovieDetail detailSearch(String uid) {

        Optional<MovieDetail> mvdls = this.movieDetailRepository.findByMovieunique(uid);

        return mvdls.get();

    }

    @Override
    public Boolean detaildelete() {
        return null;
    }
}
