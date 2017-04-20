package net.rorarius.repository;

import net.rorarius.data.Url;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UrlRepository extends MongoRepository<Url, Integer> {

    List<Url> findByUserId(Integer userId);

    Url findByLongUrl(String longUrl);

    Url findByShortUrl(String shortUrl);
}
