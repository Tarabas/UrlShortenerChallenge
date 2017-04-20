package net.rorarius.service;

import net.rorarius.data.User;
import net.rorarius.exception.InvalidLoginDataException;
import net.rorarius.exception.LoginFailedException;
import net.rorarius.exception.UserExistsException;

public interface UserService {

    String loginUser(String email, String password) throws InvalidLoginDataException, LoginFailedException;

    User registerUser(String email, String password) throws UserExistsException;

    boolean userExists(String email);


}
