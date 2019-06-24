package mari.hans.movie_information.Domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class MovieDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rank;


    private String genre;

    private String country;

    @Column(name = "time")
    private String running_time;

    private String old;

    private String audience;

    @Column(name="uniques")
    private String movieunique;



    public MovieDetail(String rank, String genre, String country, String running_time, String old,String audience,String movie_unique){
        this.rank = rank;
        this.genre = genre;
        this.country = country;
        this.running_time = running_time;
        this.old = old;
        this.audience = audience;
        this.movieunique = movie_unique;
    }



}
