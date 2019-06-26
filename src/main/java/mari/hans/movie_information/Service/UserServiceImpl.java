package mari.hans.movie_information.Service;

import mari.hans.movie_information.DAO.UserRepository;
import mari.hans.movie_information.Domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User Login(String userid, String password) { //로그인
        Optional<User> loginuser =this.userRepository.findByUseridAndPassword(userid, password);
        return loginuser.orElseGet(null);
    }

    @Override
    public User useradd(User user) { //회원가입
        Optional<User> found =this.userRepository.findByUserid(user.getUserid());
        if(found.isPresent()) return null;

        return this.userRepository.save(user);
    }
}
