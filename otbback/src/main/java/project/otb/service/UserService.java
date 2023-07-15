package project.otb.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.otb.entity.User;
import project.otb.repositiry.UserRepository;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User create(final User entity){
        if(entity == null || entity.getUsername() == null){
            throw new RuntimeException("아이디를 사용할 수 있습니다.");
        }
        final String username = entity.getUsername();
        if(userRepository.existsByUsername(username)){
            log.warn("username Already Exists!, {}", username);
            throw new RuntimeException("아이디를 사용할 수 없습니다.");
        }
        return userRepository.save(entity);
    }
    public User getByCredentials(final String username, final String password){
        return userRepository.findByUsernameAndPassword(username,password);
    }
}