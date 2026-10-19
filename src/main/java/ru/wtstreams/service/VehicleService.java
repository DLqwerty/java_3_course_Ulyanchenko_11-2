package ru.wtstreams.service;

import ru.wtstreams.model.Vehicle;
import ru.wtstreams.repository.VehicleRepository;

import java.util.List;

public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public Vehicle add(String name, String nation, String type, double br) {
        if (name == null || name.isBlank()) {
            throw new ServiceException("Название техники не может быть пустым");
        }
        if (nation == null || nation.isBlank()) {
            throw new ServiceException("Нация не может быть пустой");
        }
        if (type == null || type.isBlank()) {
            throw new ServiceException("Тип техники не может быть пустым");
        }
        if (br < 1.0 || br > 12.0) {
            throw new ServiceException("Боевой рейтинг должен быть в диапазоне от 1.0 до 12.0");
        }
        if (vehicleRepository.findByName(name).isPresent()) {
            throw new ServiceException("Техника с названием '" + name + "' уже существует");
        }
        Vehicle vehicle = new Vehicle(null, name, nation, type, br);
        return vehicleRepository.save(vehicle);
    }

    public List<Vehicle> getAll() {
        return vehicleRepository.findAll();
    }

    public Vehicle getById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Техника с id=" + id + " не найдена"));
    }

    public boolean exists(Long id) {
        return vehicleRepository.findById(id).isPresent();
    }
}
