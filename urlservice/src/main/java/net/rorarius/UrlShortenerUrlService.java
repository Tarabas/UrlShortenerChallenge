package net.rorarius;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "net.rorarius.repository")
@SpringBootApplication
public class UrlShortenerUrlService {

    public static void main(String[] args) {
        new SpringApplicationBuilder(UrlShortenerUrlService.class).web(true).run(args);
    }
}
