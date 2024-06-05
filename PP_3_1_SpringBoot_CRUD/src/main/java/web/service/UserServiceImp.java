package web.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.model.User;
import web.repository.UserRepository;

@Service
@Transactional
public class UserServiceImp implements UserService {

    private final UserRepository repository;

    @Autowired
    public UserServiceImp(UserRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void saveUser(User user) {
        repository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

}
