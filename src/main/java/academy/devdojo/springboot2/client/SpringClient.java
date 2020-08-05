package academy.devdojo.springboot2.client;

import academy.devdojo.springboot2.domain.Anime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
public class SpringClient {

    public static void main(String[] args) {
        ResponseEntity<Anime> animeResponseEntity = new RestTemplate()
                .getForEntity("http://localhost:8080/animes/{id}", Anime.class, 2);

        log.info("Response Entity {}", animeResponseEntity);

        Anime anime = new RestTemplate()
                .getForObject("http://localhost:8080/animes/{id}", Anime.class, 2);

        log.info("Anime {}", anime);

        ResponseEntity<List<Anime>> exchangeAnimeList = new RestTemplate()
                .exchange("http://localhost:8080/animes", HttpMethod.GET, null, new ParameterizedTypeReference<List<Anime>>() {});

        log.info("Anime list {}", exchangeAnimeList);
    }

}
