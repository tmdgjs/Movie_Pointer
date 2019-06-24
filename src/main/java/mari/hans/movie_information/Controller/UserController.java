package mari.hans.movie_information.Controller;

import mari.hans.movie_information.Domain.User;
import mari.hans.movie_information.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login/{id}/{password}")
    public User Login(@PathVariable String id, @PathVariable String password){
        return this.userService.Login(id,password);
    }

    @PostMapping("/signup")
    public User Signup(@RequestBody User user){
        return this.userService.useradd(user);
    }
}
