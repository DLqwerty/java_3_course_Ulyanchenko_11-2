package ru.wtstreams.repository.inmemory;

import ru.wtstreams.model.Stream;
import ru.wtstreams.repository.StreamRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryStreamRepository implements StreamRepository {

    private final Map<Long, Stream> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    @Override
    public Stream save(Stream stream) {
        if (stream.getId() == null) {
            stream.setId(idGenerator.incrementAndGet());
        }
        storage.put(stream.getId(), stream);
        return stream;
    }

    @Override
    public Optional<Stream> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Stream> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<Stream> findByStreamerId(Long streamerId) {
        return storage.values().stream()
                .filter(stream -> stream.getStreamerId().equals(streamerId))
                .toList();
    }

    @Override
    public List<Stream> findLiveStreams() {
        return storage.values().stream()
                .filter(Stream::isLive)
                .toList();
    }

    @Override
    public boolean update(Stream stream) {
        if (stream.getId() == null || !storage.containsKey(stream.getId())) {
            return false;
        }
        storage.put(stream.getId(), stream);
        return true;
    }

    @Override
    public boolean deleteById(Long id) {
        return storage.remove(id) != null;
    }
}
