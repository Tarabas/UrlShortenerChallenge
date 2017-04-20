import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import net.rorarius.UrlShortenerRedirector;
import net.rorarius.config.MongoConfiguration;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder.newMongoDbRule;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@ComponentScan( basePackages = "net.rorarius")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT,
        classes = { UrlShortenerRedirector.class, MongoConfiguration.class })
public class RedirectorIntegrationTest {

    private RestTemplate restTemplate;

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private WebApplicationContext applicationContext;

    @Rule
    public MongoDbRule mongoDbRule = newMongoDbRule().defaultSpringMongoDb("testdb");

    @Before
    public void setUp() {
        HttpClient httpClient = HttpClientBuilder.create().disableRedirectHandling().build();
        restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));
    }

    @Test
    @UsingDataSet(locations = {"/testdata/integration-url-exists.json"})
    public void testRedirectUrlExists() {
        ResponseEntity response = restTemplate.
                getForEntity( "http://localhost:"+randomServerPort+"/a", String.class);

        List<String> locationHeader = response.getHeaders().get("Location");

        assertThat(response.getStatusCode(), equalTo(HttpStatus.FOUND));
        assertThat(locationHeader.get(0), equalTo("https://www.xxx.de"));
    }

    @Test
    @UsingDataSet(locations = {"/testdata/integration-url-not-exists.json"})
    public void testRedirectUrlNotExists() {

        try {
            ResponseEntity<String> response = restTemplate.
                    getForEntity( "http://localhost:"+randomServerPort+"/a", String.class);

        } catch (HttpClientErrorException ex) {
            assertThat(ex.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
        }
    }
}
