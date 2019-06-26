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
    public Comment addcomment(Comment comment) { //코멘트 추가

        return this.commentRepository.save(comment);

    }

    @Override
    public List<Comment> searchcomment(String uid) { //코멘트 리스트
        List<Comment> mvls = this.commentRepository.findByMovieunique(uid);

        return mvls;
    }

}
