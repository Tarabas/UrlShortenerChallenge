package net.rorarius.service;

import net.rorarius.converter.IdToStringConverter;
import net.rorarius.data.Url;
import net.rorarius.data.User;
import net.rorarius.exception.LoginTokenExpiredException;
import net.rorarius.repository.CounterDao;
import net.rorarius.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShortenerServiceImpl implements ShortenerService {

    @Autowired
    UrlRepository urlRepository;

    @Autowired
    UserService userService;

    @Autowired
    IdToStringConverter idConverter;

    @Autowired
    CounterDao counterDao;

    @Override
    public Url getShortenedUrl(String longUrl) {
        return urlRepository.findByLongUrl(longUrl);
    }

    @Override
    public Url addShortenedUrl(String longUrl, String token) throws LoginTokenExpiredException {
        int count = counterDao.getNextCount("url");
        User user = null;

        if (token != null) {
            user = userService.getUserFromToken(token);
        }

        Url shortenedUrl = createNewUrl(longUrl, count, user);
        shortenedUrl = urlRepository.save(shortenedUrl);

        return shortenedUrl;
    }

    private Url createNewUrl(String longUrl, int count, User user) {
        Url shortenedUrl = new Url();
        shortenedUrl.setCallCount(0);
        shortenedUrl.setShortenCount(1);
        shortenedUrl.setLongUrl(longUrl);
        shortenedUrl.setSequence(count);
        if (user != null) {
            shortenedUrl.setUserId(user.getId());
        }
        shortenedUrl.setShortUrl(idConverter.encodeId(count));

        return shortenedUrl;
    }

    public void addShortenCount(Url shortenedUrl) {
        shortenedUrl.setShortenCount(updateCount(shortenedUrl.getShortenCount()));
        urlRepository.save(shortenedUrl);
    }

    private Integer updateCount(Integer count) {
        if (count != null) {
            count++;
        } else {
            count = 1;
        }

        return count;
    }
}
