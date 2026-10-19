package ru.wtstreams.repository.inmemory;

import ru.wtstreams.model.Streamer;
import ru.wtstreams.repository.StreamerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryStreamerRepository implements StreamerRepository {

    private final Map<Long, Streamer> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    @Override
    public Streamer save(Streamer streamer) {
        if (streamer.getId() == null) {
            streamer.setId(idGenerator.incrementAndGet());
        }
        storage.put(streamer.getId(), streamer);
        return streamer;
    }

    @Override
    public Optional<Streamer> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Streamer> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<Streamer> findByNickname(String nickname) {
        return storage.values().stream()
                .filter(streamer -> streamer.getNickname().equalsIgnoreCase(nickname))
                .findFirst();
    }

    @Override
    public boolean update(Streamer streamer) {
        if (streamer.getId() == null || !storage.containsKey(streamer.getId())) {
            return false;
        }
        storage.put(streamer.getId(), streamer);
        return true;
    }

    @Override
    public boolean deleteById(Long id) {
        return storage.remove(id) != null;
    }
}
