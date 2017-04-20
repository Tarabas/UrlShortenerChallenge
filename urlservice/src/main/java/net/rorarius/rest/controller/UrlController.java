package net.rorarius.rest.controller;

import net.rorarius.data.Url;
import net.rorarius.exception.LoginTokenExpiredException;
import net.rorarius.rest.data.RestShortenRequest;
import net.rorarius.rest.data.RestUrlInfo;
import net.rorarius.service.ShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
public class UrlController {

    @Autowired
    ShortenerService shortenerService;

    @RequestMapping(value="/rest/shorten",
            method= RequestMethod.POST,
            produces= MediaType.APPLICATION_JSON_VALUE
    )
    public RestUrlInfo shorten(@RequestBody @Valid RestShortenRequest restShortenRequest,
                               HttpServletResponse response) throws IOException {
        try {
            Url shortenedUrl = shortenerService.getShortenedUrl(restShortenRequest.getLongUrl());

            if (shortenedUrl == null) {
                shortenedUrl = shortenerService.addShortenedUrl(restShortenRequest.getLongUrl(), restShortenRequest.getToken());
            } else {
                shortenerService.addShortenCount(shortenedUrl);
            }

            RestUrlInfo restShortLink = new RestUrlInfo(restShortenRequest.getLongUrl(), shortenedUrl.getShortUrl());

            return restShortLink;
        } catch (LoginTokenExpiredException lte) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Login Token expired!");
            return null;
        }
    }
}
