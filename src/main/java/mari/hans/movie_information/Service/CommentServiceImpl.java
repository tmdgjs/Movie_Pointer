package mari.hans.movie_information.Service;

import mari.hans.movie_information.DAO.CommentRepository;
import mari.hans.movie_information.Domain.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;


    @Override
    public Comment addcomment(Comment comment) {

        return this.commentRepository.save(comment);

    }

    @Override
    public List<Comment> searchcomment(String uid) {
        List<Comment> mvls = this.commentRepository.findByMovieunique(uid);

        return mvls;
    }


}
