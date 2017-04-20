import net.rorarius.data.Url;
import net.rorarius.repository.UrlRepository;
import net.rorarius.service.ShortenerService;
import net.rorarius.service.ShortenerServiceImpl;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

public class ShortenerServiceTest {

    ShortenerService shortenerService;
    UrlRepository urlRepositoryMock;

    @Before
    public void setUp() {
        urlRepositoryMock = Mockito.mock(UrlRepository.class);
        shortenerService = new ShortenerServiceImpl(urlRepositoryMock);
    }

    @Test
    public void urlExists() {
        Url returnedUrl = new Url();
        returnedUrl.setSequence(1);
        returnedUrl.setShortenCount(1);
        returnedUrl.setShortUrl("a");
        returnedUrl.setLongUrl("https://www.heise.de");
        returnedUrl.setCallCount(1);
        returnedUrl.setId(new ObjectId());

        when(urlRepositoryMock.findByShortUrl(any())).thenReturn(returnedUrl);

        String longUrl = shortenerService.getLongUrl("a");

        assertThat(longUrl, equalTo(returnedUrl.getLongUrl()));
    }

    @Test
    public void urlDoesNotExist() {
        when(urlRepositoryMock.findByShortUrl(any())).thenReturn(null);
        String longUrl = shortenerService.getLongUrl("a");
        assertThat(longUrl, equalTo(null));
    }
}
