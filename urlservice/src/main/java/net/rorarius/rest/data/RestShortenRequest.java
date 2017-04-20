package net.rorarius.rest.data;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;

public class RestShortenRequest {

    @NotNull
    @URL
    private String longUrl;
    private String token;

    public RestShortenRequest() {
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
