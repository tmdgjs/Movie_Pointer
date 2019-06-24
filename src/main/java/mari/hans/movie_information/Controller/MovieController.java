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


    //info db
    @GetMapping("/movie")
    public List<MovieInformation> mv(){
        return this.crawlingService.infoadd();
    }



    @DeleteMapping("/movied")
    public boolean mvde(){
        return this.crawlingService.infodelete();
    }


    //id search
    @GetMapping("/movies/{id}")
    public MovieInformation mvname(@PathVariable Long id) {
        return this.crawlingService.infoSearch(id);
    }

    @GetMapping("/moviesall")
    public void mvall(){
        mv();
        mvd();
        mva();
        mvi();
    }

    //detail db
    @GetMapping("/moviedetail")
    public List<MovieDetail> mvd(){
        return this.crawlingDetailService.detailadd();
    }

    //actor db
    @GetMapping("/moviea")
    public List<MovieActor> mva() {return this.crawlingActorService.actoradd();}

    @GetMapping("/moviei")
    public List<MovieImage> mvi() {return this.crawlingImageService.imageadd();}


    @GetMapping("/movie/count")
    public Long moviecount(){
        return this.crawlingService.infoCount();
    }
    /*@GetMapping("/movieactor/{uniques}/{role}")
    public Optional<MovieActor> actorsearch(@PathVariable String uniques,@PathVariable String role){
        return this.crawlingActorService.movie_actorsearch(uniques, role);
    }*/

    //detail search
    @GetMapping("/movie/{uid}")
    public MovieInformation mvuniquesearch(@PathVariable String uid)
    {
        return this.crawlingService.infoSearch1(uid);
    }

    @GetMapping("/movied/{uid}")
    public MovieDetail mvdsearch(@PathVariable String uid) { return this.crawlingDetailService.detailSearch(uid);}

    @GetMapping("/moviei/{uid}")
    public MovieImage mvimagesearch(@PathVariable String uid){
        return this.crawlingImageService.imagesearch(uid) ;
    }

    @GetMapping("/moviea/{uid}")
    public List<MovieActor> mvactorsearch(@PathVariable String uid){
        return this.crawlingActorService.actorsearch(uid);
    }



}
