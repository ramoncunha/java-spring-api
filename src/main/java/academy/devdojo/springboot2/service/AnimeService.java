package academy.devdojo.springboot2.service;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.repository.AnimeRepository;
import academy.devdojo.springboot2.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor // construtor para atributos "final"
public class AnimeService {

    private final Utils utils;
    private final AnimeRepository animeRepository;

    public List<Anime> listAll() {
        return this.animeRepository.findAll();
    }

    public List<Anime> findByName(String name) {
        return this.animeRepository.findByName(name);
    }

    public Anime findById(int id) {
        return utils.findAnimeOrThrowNotFound(id, animeRepository);
    }

    //@Transactional(rollbackFor = Exception.class) -> para dar rollback quando não for uma RunTime
    @Transactional
    public Anime save(Anime anime) {
        return this.animeRepository.save(anime);
    }

    @Transactional
    public void delete(int id) {
        this.animeRepository.delete(utils.findAnimeOrThrowNotFound(id, animeRepository));
    }

    @Transactional
    public void update(Anime anime) {
        animeRepository.save(anime);
    }
}
