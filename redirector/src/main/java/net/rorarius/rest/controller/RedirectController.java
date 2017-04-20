package net.rorarius.rest.controller;

import net.rorarius.service.ShortenerService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.IOException;

@RestController
public class RedirectController {

    ShortenerService shortenerService;

    public RedirectController(ShortenerService shortenerService) {
        if (shortenerService == null) {
            throw new IllegalArgumentException("ShortenerService cannot be null");
        }

        this.shortenerService = shortenerService;
    }

    @RequestMapping(value="/{id}", method= RequestMethod.GET)
    public void register(@PathVariable
                         @NotNull @Min(1) @Max(255) @Pattern(regexp = "(\\w\\d)+") String id,
                         HttpServletResponse response) throws IOException {
        try {
            String longUrl = shortenerService.getLongUrl(id);

            if (longUrl != null) {
                response.sendRedirect(longUrl);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}
