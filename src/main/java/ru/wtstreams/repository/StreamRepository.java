package ru.wtstreams.repository;

import ru.wtstreams.model.Stream;

import java.util.List;
import java.util.Optional;

public interface StreamRepository {

    Stream save(Stream stream);

    Optional<Stream> findById(Long id);

    List<Stream> findAll();

    List<Stream> findByStreamerId(Long streamerId);

    List<Stream> findLiveStreams();

    boolean update(Stream stream);

    boolean deleteById(Long id);
}
