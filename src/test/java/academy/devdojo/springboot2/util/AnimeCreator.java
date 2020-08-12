package academy.devdojo.springboot2.util;

import academy.devdojo.springboot2.domain.Anime;

public class AnimeCreator {

    public static Anime createAnimeToBeSaved() {
        return Anime.builder()
                .name("Tensor Data")
                .build();
    }

    public static Anime createValidAnime() {
        return Anime.builder()
                .name("Tensor Data")
                .id(1)
                .build();
    }

    public static Anime createValidUpdatedAnime() {
        return Anime.builder()
                .name("Tensor Data")
                .id(1)
                .build();
    }

}
