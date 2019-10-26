package mari.hans.movie_information.DAO;

import mari.hans.movie_information.Domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByMovieunique(String movieunique);

}
