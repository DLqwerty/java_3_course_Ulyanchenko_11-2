package ru.wtstreams.service;

import ru.wtstreams.model.Stream;
import ru.wtstreams.repository.StreamRepository;

import java.time.LocalDateTime;
import java.util.List;

public class StreamService {

    private final StreamRepository streamRepository;
    private final StreamerService streamerService;
    private final VehicleService vehicleService;

    public StreamService(StreamRepository streamRepository,
                         StreamerService streamerService,
                         VehicleService vehicleService) {
        this.streamRepository = streamRepository;
        this.streamerService = streamerService;
        this.vehicleService = vehicleService;
    }

    public Stream createDraft(Long streamerId, Long vehicleId, String title) {
        if (!streamerService.exists(streamerId)) {
            throw new ServiceException("Стример с id=" + streamerId + " не найден");
        }
        if (!vehicleService.exists(vehicleId)) {
            throw new ServiceException("Техника с id=" + vehicleId + " не найдена");
        }
        if (title == null || title.isBlank()) {
            throw new ServiceException("Название стрима не может быть пустым");
        }
        Stream stream = new Stream(null, streamerId, vehicleId, title, null, 0, false);
        return streamRepository.save(stream);
    }

    public Stream start(Long streamId) {
        Stream stream = getById(streamId);
        if (stream.isLive()) {
            throw new ServiceException("Стрим id=" + streamId + " уже запущен");
        }
        stream.setLive(true);
        stream.setStartTime(LocalDateTime.now());
        stream.setViewers(0);
        streamRepository.update(stream);
        return stream;
    }

    public Stream stop(Long streamId) {
        Stream stream = getById(streamId);
        if (!stream.isLive()) {
            throw new ServiceException("Стрим id=" + streamId + " не запущен");
        }
        stream.setLive(false);
        streamRepository.update(stream);
        return stream;
    }

    public Stream updateViewers(Long streamId, int viewers) {
        if (viewers < 0) {
            throw new ServiceException("Количество зрителей не может быть отрицательным");
        }
        Stream stream = getById(streamId);
        stream.setViewers(viewers);
        streamRepository.update(stream);
        return stream;
    }

    public List<Stream> getAll() {
        return streamRepository.findAll();
    }

    public List<Stream> getLiveStreams() {
        return streamRepository.findLiveStreams();
    }

    public List<Stream> getByStreamerId(Long streamerId) {
        return streamRepository.findByStreamerId(streamerId);
    }

    public Stream getById(Long id) {
        return streamRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Стрим с id=" + id + " не найден"));
    }

    public boolean exists(Long id) {
        return streamRepository.findById(id).isPresent();
    }
}
