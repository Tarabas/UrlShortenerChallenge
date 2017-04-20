package net.rorarius.service;

import net.rorarius.data.User;
import net.rorarius.exception.LoginTokenExpiredException;

public interface UserService {

    User getUserFromToken(String token) throws LoginTokenExpiredException;
}
