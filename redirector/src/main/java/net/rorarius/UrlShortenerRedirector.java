package net.rorarius;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "net.rorarius.repository")
@SpringBootApplication
public class UrlShortenerRedirector {

    public static void main(String[] args) {
        new SpringApplicationBuilder(UrlShortenerRedirector.class).web(true).run(args);
    }
}
