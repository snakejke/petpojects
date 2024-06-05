package web.service;


import java.util.List;
import web.model.User;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(Long id);

    void saveUser(User user);

    void deleteUser(Long id);

}
