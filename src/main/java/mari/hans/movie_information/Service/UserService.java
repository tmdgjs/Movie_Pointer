package mari.hans.movie_information.Service;

import mari.hans.movie_information.Domain.User;

public interface UserService {

    User Login (String userid, String password);

    User useradd(User user);

}
