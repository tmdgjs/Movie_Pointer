package mari.hans.movie_information.Domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String userid;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;


    public User (String userid, String password, String name){
        this.userid = userid;
        this.password= password;
        this.name = name;

    }

    public User(User u){
        this.userid = u.getUserid();
        this.password = u.getPassword();
        this.name = u.getName();

    }


}
