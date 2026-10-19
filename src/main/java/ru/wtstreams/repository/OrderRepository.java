package ru.wtstreams.repository;

import ru.wtstreams.model.StreamOrder;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    StreamOrder save(StreamOrder order);

    Optional<StreamOrder> findById(Long id);

    List<StreamOrder> findAll();

    List<StreamOrder> findByStreamId(Long streamId);

    boolean update(StreamOrder order);

    boolean deleteById(Long id);
}
