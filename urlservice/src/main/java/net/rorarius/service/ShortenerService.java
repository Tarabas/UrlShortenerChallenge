package net.rorarius.service;

import net.rorarius.data.Url;
import net.rorarius.exception.LoginTokenExpiredException;

public interface ShortenerService {
    Url getShortenedUrl(String longUrl);

    Url addShortenedUrl(String longUrl, String token) throws LoginTokenExpiredException;

    void addShortenCount(Url shortenedUrl);
}
