package mari.hans.movie_information.Domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor

public class MovieInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String movie_name;

    @Column(name="info")
    @Size(max = 2147483646)
    private String movie_info;

    @Column(name="href")
    private String movie_imagehref;

    @JsonFormat(pattern = "yy-MM-dd")
    private Date movie_startday;

    @Column(name = "uniques")
    private String movieunique;


    public MovieInformation(String movie_name, String movie_info,String movie_imagehref,Date movie_startday, String movie_unique){

        this.movie_name = movie_name;
        this.movie_info = movie_info;
        this.movie_imagehref = movie_imagehref;
        this.movie_startday = movie_startday;
        this.movieunique = movie_unique;

    }

    public MovieInformation(MovieInformation m){
        this.id = m.getId();
        this.movie_name = m.getMovie_name();
        this.movie_info = m.getMovie_info();
        this.movie_imagehref = m.getMovie_imagehref();
        this.movie_startday = m.getMovie_startday();
        this.movieunique = m.getMovieunique();
    }
}
