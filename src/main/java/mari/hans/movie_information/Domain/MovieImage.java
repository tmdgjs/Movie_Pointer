package mari.hans.movie_information.Domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class MovieImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="uniques")
    private String movieunique;

    private String image1;

    private String image2;

    private String image3;

    private String image4;

    private String image5;


    public MovieImage(String movieunique, String image1, String image2, String image3, String image4, String image5){
        this.movieunique = movieunique;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
        this.image5 = image5;

    }

}
