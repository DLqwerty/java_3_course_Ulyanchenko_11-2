package ru.wtstreams.service;

import ru.wtstreams.model.Streamer;
import ru.wtstreams.repository.StreamerRepository;

import java.util.List;

public class StreamerService {

    private final StreamerRepository streamerRepository;

    public StreamerService(StreamerRepository streamerRepository) {
        this.streamerRepository = streamerRepository;
    }

    public Streamer register(String nickname, String platform, int subscribers, String mainNation) {
        if (nickname == null || nickname.isBlank()) {
            throw new ServiceException("Никнейм не может быть пустым");
        }
        if (platform == null || platform.isBlank()) {
            throw new ServiceException("Платформа не может быть пустой");
        }
        if (subscribers < 0) {
            throw new ServiceException("Количество подписчиков не может быть отрицательным");
        }
        if (streamerRepository.findByNickname(nickname).isPresent()) {
            throw new ServiceException("Стример с ником '" + nickname + "' уже существует");
        }
        Streamer streamer = new Streamer(null, nickname, platform, subscribers, mainNation);
        return streamerRepository.save(streamer);
    }

    public List<Streamer> getAll() {
        return streamerRepository.findAll();
    }

    public Streamer getById(Long id) {
        return streamerRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Стример с id=" + id + " не найден"));
    }

    public boolean exists(Long id) {
        return streamerRepository.findById(id).isPresent();
    }
}
