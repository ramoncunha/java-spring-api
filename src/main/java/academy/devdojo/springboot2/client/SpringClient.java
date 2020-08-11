package academy.devdojo.springboot2.client;

import academy.devdojo.springboot2.domain.Anime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
public class SpringClient {

    public static void main(String[] args) {
//        ResponseEntity<Anime> animeResponseEntity = new RestTemplate()
//                .getForEntity("http://localhost:8080/animes/{id}", Anime.class, 2);
//
//        log.info("Response Entity {}", animeResponseEntity);
//
//        Anime anime = new RestTemplate()
//                .getForObject("http://localhost:8080/animes/{id}", Anime.class, 2);

//        log.info("Anime {}", anime);

        ResponseEntity<List<Anime>> exchangeAnimeList = new RestTemplate()
                .exchange("http://localhost:8080/animes", HttpMethod.GET, null, new ParameterizedTypeReference<List<Anime>>() {});

        log.info("Anime list {}", exchangeAnimeList);

        Anime overlord = Anime.builder().name("Overlord").build();
        Anime overlordSaved = new RestTemplate().postForObject("http://localhost:8080/animes", overlord, Anime.class);

        log.info("Anime created {}", overlordSaved);

        overlordSaved.setName("Steins Gate");
        ResponseEntity<Void> updatedEntity = new RestTemplate()
                .exchange("http://localhost:8080/animes", HttpMethod.PUT,
                        new HttpEntity<>(overlordSaved, createJsonHeader()), Void.class);

        log.info("Entity updated: {}", updatedEntity.getStatusCode());

        ResponseEntity<Void> deletedEntity = new RestTemplate()
                .exchange("http://localhost:8080/animes/{id}", HttpMethod.DELETE,
                        null, Void.class, overlordSaved.getId());

        log.info("Entity deleted status: {}", deletedEntity.getStatusCode());

    }

    private static HttpHeaders createJsonHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }

}
