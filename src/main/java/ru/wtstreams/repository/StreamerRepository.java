package ru.wtstreams.repository;

import ru.wtstreams.model.Streamer;

import java.util.List;
import java.util.Optional;

public interface StreamerRepository {

    Streamer save(Streamer streamer);

    Optional<Streamer> findById(Long id);

    List<Streamer> findAll();

    Optional<Streamer> findByNickname(String nickname);

    boolean update(Streamer streamer);

    boolean deleteById(Long id);
}
