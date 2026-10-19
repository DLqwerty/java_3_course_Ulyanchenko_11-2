package ru.wtstreams.repository.inmemory;

import ru.wtstreams.model.Vehicle;
import ru.wtstreams.repository.VehicleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryVehicleRepository implements VehicleRepository {

    private final Map<Long, Vehicle> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    @Override
    public Vehicle save(Vehicle vehicle) {
        if (vehicle.getId() == null) {
            vehicle.setId(idGenerator.incrementAndGet());
        }
        storage.put(vehicle.getId(), vehicle);
        return vehicle;
    }

    @Override
    public Optional<Vehicle> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Vehicle> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<Vehicle> findByName(String name) {
        return storage.values().stream()
                .filter(vehicle -> vehicle.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    @Override
    public boolean update(Vehicle vehicle) {
        if (vehicle.getId() == null || !storage.containsKey(vehicle.getId())) {
            return false;
        }
        storage.put(vehicle.getId(), vehicle);
        return true;
    }

    @Override
    public boolean deleteById(Long id) {
        return storage.remove(id) != null;
    }
}
