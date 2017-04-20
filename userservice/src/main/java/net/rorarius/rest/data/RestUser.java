package net.rorarius.rest.data;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;

public class RestUser {

    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;

    public RestUser() {
    }

    public RestUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
