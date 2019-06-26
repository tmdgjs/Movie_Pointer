package mari.hans.movie_information.Controller;

import mari.hans.movie_information.Domain.Comment;
import mari.hans.movie_information.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/comment/add")
    public Comment addcomment(@RequestBody Comment comment){
        return this.commentService.addcomment(comment);
    }

    @GetMapping("/comment/{uid}")
    public List<Comment> addcomment(@PathVariable String uid){
        return this.commentService.searchcomment(uid);
    }




}
