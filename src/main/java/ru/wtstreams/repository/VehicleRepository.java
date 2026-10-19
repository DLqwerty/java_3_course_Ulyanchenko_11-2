package ru.wtstreams.repository;

import ru.wtstreams.model.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository {

    Vehicle save(Vehicle vehicle);

    Optional<Vehicle> findById(Long id);

    List<Vehicle> findAll();

    Optional<Vehicle> findByName(String name);

    boolean update(Vehicle vehicle);

    boolean deleteById(Long id);
}
