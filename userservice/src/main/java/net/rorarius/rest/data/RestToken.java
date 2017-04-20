package net.rorarius.rest.data;

import javax.validation.constraints.NotNull;

public class RestToken {

    @NotNull
    private String token;

    public RestToken() {
    }

    public RestToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
