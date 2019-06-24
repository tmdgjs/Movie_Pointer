package mari.hans.movie_information.Domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;

    private String userid;

    @Column(name = "uniques")
    private String movieunique;


    public Comment(String comment, String userid, String movieunique){
        this.comment = comment;
        this.userid = userid;
        this.movieunique = movieunique;
    }

    public Comment(Comment c){
        this.comment = c.getComment();
        this.userid = c.getUserid();
        this.movieunique = c.getMovieunique();
    }

}
