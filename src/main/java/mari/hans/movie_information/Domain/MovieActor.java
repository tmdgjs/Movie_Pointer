package mari.hans.movie_information.Domain;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class MovieActor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "aname")
    private String actor_name;

    @Column(name = "arole")
    private String actor_role;

    @Column(name = "aimg")
    private String actor_img;

    @Column(name = "uniques")
    private String movieunique;

    public MovieActor(String actor_name, String actor_role, String actor_img, String movieunique){
        this.actor_name = actor_name;
        this.actor_role = actor_role;
        this.actor_img = actor_img;
        this.movieunique = movieunique;
    }


}
