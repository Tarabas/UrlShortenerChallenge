package net.rorarius.service;

import net.rorarius.config.AppConfig;
import net.rorarius.data.User;
import net.rorarius.data.UserToken;
import net.rorarius.enums.UserTypeEnum;
import net.rorarius.exception.InvalidLoginDataException;
import net.rorarius.exception.LoginFailedException;
import net.rorarius.exception.UserExistsException;
import net.rorarius.helper.PasswordGenerator;
import net.rorarius.repository.UserRepository;
import net.rorarius.repository.UserTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    AppConfig appConfig;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserTokenRepository tokenRepository;

    @Override
    public String loginUser(String email, String password) throws InvalidLoginDataException, LoginFailedException {
        validateLoginDataNotNull(email, password);

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new LoginFailedException("Login failed, Email/Password combination not found!");
        } else {
            boolean passwordValid = PasswordGenerator.checkPassword(password, user.getPassword());

            if (!passwordValid) {
                throw new LoginFailedException("Login failed, Email/Password combination not found!");
            }
        }

        long expires = System.currentTimeMillis()+(appConfig.getExpireMinutes()*60000);

        UserToken token = new UserToken(user.getId(), UUID.randomUUID().toString(), expires);
        tokenRepository.save(token);

        return token.getToken();
    }

    @Override
    public User registerUser(String email, String password) throws UserExistsException {
        if (userExists(email)) {
            throw new UserExistsException("User already exists!");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(PasswordGenerator.generatePasswordHash(password));
        user.setType(UserTypeEnum.USER.getType());

        return userRepository.save(user);
    }

    @Override
    public boolean userExists(String email) {
        if (userRepository.countByEmail(email) > 0) {
            return true;
        } else {
            return false;
        }
    }

    private void validateLoginDataNotNull(String email, String password) throws InvalidLoginDataException {
        if (email == null) {
            throw new InvalidLoginDataException("Email must not be null");
        }

        if (password == null) {
            throw new InvalidLoginDataException("Password must not be null");
        }
    }
}
