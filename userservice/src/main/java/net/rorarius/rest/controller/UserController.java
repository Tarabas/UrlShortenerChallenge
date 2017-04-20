package net.rorarius.rest.controller;

import net.rorarius.exception.InvalidLoginDataException;
import net.rorarius.exception.LoginFailedException;
import net.rorarius.exception.UserExistsException;
import net.rorarius.rest.data.RestToken;
import net.rorarius.rest.data.RestUser;
import net.rorarius.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value="/rest/register",
            method= RequestMethod.POST,
            produces= MediaType.APPLICATION_JSON_VALUE
    )
    public void register(@RequestBody RestUser user,
                         HttpServletResponse response) throws IOException {


        try {
            userService.registerUser(user.getEmail(), user.getPassword());
            response.sendError(HttpServletResponse.SC_OK);
        } catch (UserExistsException uex) {
            response.sendError(HttpServletResponse.SC_CONFLICT);
        }
    }

    @RequestMapping(value="/rest/login",
            method= RequestMethod.POST,
            produces= MediaType.APPLICATION_JSON_VALUE
    )
    public RestToken login(@RequestBody RestUser user,
                           HttpServletResponse response) throws IOException {

        try {
            return new RestToken(userService.loginUser(user.getEmail(), user.getPassword()));
        } catch (InvalidLoginDataException idx) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        } catch (LoginFailedException lfe){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }
    }
}
