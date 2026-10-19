package ru.wtstreams.repository.inmemory;

import ru.wtstreams.model.StreamOrder;
import ru.wtstreams.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryOrderRepository implements OrderRepository {

    private final Map<Long, StreamOrder> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    @Override
    public StreamOrder save(StreamOrder order) {
        if (order.getId() == null) {
            order.setId(idGenerator.incrementAndGet());
        }
        storage.put(order.getId(), order);
        return order;
    }

    @Override
    public Optional<StreamOrder> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<StreamOrder> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<StreamOrder> findByStreamId(Long streamId) {
        return storage.values().stream()
                .filter(order -> order.getStreamId().equals(streamId))
                .toList();
    }

    @Override
    public boolean update(StreamOrder order) {
        if (order.getId() == null || !storage.containsKey(order.getId())) {
            return false;
        }
        storage.put(order.getId(), order);
        return true;
    }

    @Override
    public boolean deleteById(Long id) {
        return storage.remove(id) != null;
    }
}
