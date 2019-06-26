package mari.hans.movie_information.Controller;

import mari.hans.movie_information.Domain.MovieActor;
import mari.hans.movie_information.Domain.MovieDetail;
import mari.hans.movie_information.Domain.MovieImage;
import mari.hans.movie_information.Domain.MovieInformation;
import mari.hans.movie_information.Service.CrawlingActorService;
import mari.hans.movie_information.Service.CrawlingDetailService;
import mari.hans.movie_information.Service.CrawlingImageService;
import mari.hans.movie_information.Service.CrawlingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class MovieController {

    @Autowired
    private CrawlingService crawlingService;

    @Autowired
    private CrawlingDetailService crawlingDetailService;

    @Autowired
    private CrawlingActorService crawlingActorService;

    @Autowired
    private CrawlingImageService crawlingImageService;


    //scheduled
    @Scheduled(cron = "0 0 10 * * *")
    public void scheduleFixedRateTask() { // 매일 10시에 자동 실행

        try{
            movie_all_content_delete();
        }
        catch (Exception e){

        }

        movie_all_content_add();

    }

    //All content

    @GetMapping("/movies/add")
    public void movie_all_content_add(){
        movie_infoadd();
        movie_detailadd();
        movie_actoradd();
        movie_imageadd();
    }

    @DeleteMapping("/movies/delete")
    public void movie_all_content_delete(){
        movie_infodelete();
        movie_detaildelete();
        movie_actordelete();
        movie_imagedelete();
    }

    //info db

    @GetMapping("/movie/infoadd")
    public List<MovieInformation> movie_infoadd(){
        return this.crawlingService.infoadd();
    }

    @DeleteMapping("/movie/infodelete")
    public boolean movie_infodelete(){
        return this.crawlingService.infodelete();
    }

    //detail db

    @GetMapping("/movie/detailadd")
    public List<MovieDetail> movie_detailadd(){
        return this.crawlingDetailService.detailadd();
    }

    @DeleteMapping("/movie/detaildelete")
    public boolean movie_detaildelete(){
        return this.crawlingDetailService.detaildelete();
    }

    //actor db

    @GetMapping("/movie/actoradd")
    public List<MovieActor> movie_actoradd() {return this.crawlingActorService.actoradd();}

    @DeleteMapping("/movie/actordelete")
    public boolean movie_actordelete(){
        return this.crawlingActorService.actordelete();
    }

    //image db

    @GetMapping("/movie/imageadd")
    public List<MovieImage> movie_imageadd() {return this.crawlingImageService.imageadd();}

    @DeleteMapping("/movie/imagedelete")
    public boolean movie_imagedelete(){
        return this.crawlingImageService.imagedelete();
    }

    //movie_unique_num search

    @GetMapping("/movie/infosearch/{uid}")
    public MovieInformation mvuniquesearch(@PathVariable String uid)
    {
        return this.crawlingService.infouniqueSearch(uid);
    }

    @GetMapping("/movie/deatilsearch/{uid}")
    public MovieDetail mvdsearch(@PathVariable String uid) { return this.crawlingDetailService.detailSearch(uid);}

    @GetMapping("/movie/imagesearch/{uid}")
    public MovieImage mvimagesearch(@PathVariable String uid){
        return this.crawlingImageService.imagesearch(uid) ;
    }

    @GetMapping("/movie/actorsearch/{uid}")
    public List<MovieActor> mvactorsearch(@PathVariable String uid){
        return this.crawlingActorService.actorsearch(uid);
    }

    //id search

    @GetMapping("/movie/{id}")
    public MovieInformation mvname(@PathVariable Long id) {
        return this.crawlingService.infoSearch(id);
    }

    //전체 갯수

    @GetMapping("/movie/count")
    public Long moviecount(){
        return this.crawlingService.infoCount();
    }

    // 첫번째 값

    @GetMapping("/movie/fid")
    public MovieInformation mvfirst(){
        return this.crawlingService.infoSearchfirstid();
    }

}
