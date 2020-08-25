package academy.devdojo.springboot2.repository;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.util.AnimeCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Anime Repository Test")
//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class) // retira underscore do nome das classes de teste
class AnimeRepositoryTest {

    @Autowired
    private AnimeRepository animeRepository;

    @Test
    @DisplayName("Save creates anime when successful")
    public void save_PersistAnime_WhenSuccessful() {
        Anime anime = AnimeCreator.createAnimeToBeSaved();
        Anime savedAnime = this.animeRepository.save(anime);
        Assertions.assertThat(savedAnime.getId()).isNotNull();
    }

    @Test
    @DisplayName("Save updates anime when successful")
    public void save_UpdateAnime_WhenSuccessful() {
        Anime anime = AnimeCreator.createAnimeToBeSaved();
        Anime savedAnime = this.animeRepository.save(anime);

        savedAnime.setName("Ramon");
        Anime updatedAnime = this.animeRepository.save(savedAnime);

        Assertions.assertThat(savedAnime.getId()).isNotNull();
        Assertions.assertThat(savedAnime.getName()).isEqualTo(updatedAnime.getName());
    }

    @Test
    @DisplayName("Delete removes anime when successful")
    public void delete_RemoveAnime_WhenSuccessful() {
        Anime anime = AnimeCreator.createAnimeToBeSaved();
        Anime savedAnime = this.animeRepository.save(anime);

        this.animeRepository.delete(savedAnime);

        Optional<Anime> id = this.animeRepository.findById(savedAnime.getId());

        Assertions.assertThat(id.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Find by name returns anime when successful")
    public void findByName_ReturnAnime_WhenSuccessful() {
        Anime anime = AnimeCreator.createAnimeToBeSaved();
        Anime savedAnime = this.animeRepository.save(anime);

        String name = anime.getName();

        List<Anime> animeList = this.animeRepository.findByName(name);

        Assertions.assertThat(animeList).isNotEmpty();
        Assertions.assertThat(animeList).contains(savedAnime);
    }

    @Test
    @DisplayName("Find by name returns empty list when anime not found")
    public void findByName_ReturnEmptyList_WhenAnimeNotFound() {
        String name = "fake-name";

        List<Anime> animeList = this.animeRepository.findByName(name);

        Assertions.assertThat(animeList).isEmpty();
    }

    @Test
    @DisplayName("Save throw constraint violation exception when name is empty")
    public void save_ThrowConstraintViolationException_WhenNameIsEmpty() {
        Anime anime = new Anime();

//        Assertions.assertThatThrownBy(() -> this.animeRepository.save(anime))
//            .isInstanceOf(ConstraintViolationException.class);
        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
            .isThrownBy(() -> this.animeRepository.save(anime))
            .withMessageContaining("Name of this anime, cannot be empty");
    }

}