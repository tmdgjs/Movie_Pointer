package mari.hans.movie_information.Service;

import mari.hans.movie_information.Domain.Comment;

import java.util.List;

public interface CommentService {

    Comment addcomment(Comment comment);

    List<Comment> searchcomment(String uid);


}
