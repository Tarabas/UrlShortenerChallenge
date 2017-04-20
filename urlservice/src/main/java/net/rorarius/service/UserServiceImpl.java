package net.rorarius.service;

import net.rorarius.data.User;
import net.rorarius.data.UserToken;
import net.rorarius.exception.LoginTokenExpiredException;
import net.rorarius.repository.UserRepository;
import net.rorarius.repository.UserTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    UserTokenRepository tokenRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public User getUserFromToken(String token) throws LoginTokenExpiredException {
        UserToken userToken = tokenRepository.findByToken(token);

        if (userToken == null || userToken.getExpires().longValue() < System.currentTimeMillis()) {
            throw new LoginTokenExpiredException("Logintoken expired!");
        } else {
            return userRepository.findById(userToken.getId());
        }
    }
}
