package ru.practicum.fallback;

import org.springframework.stereotype.Component;
import ru.practicum.client.StatClient;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.exception.ServerUnavailableException;

import java.util.List;

@Component
public class StatFallback implements StatClient {

    @Override
    public void save(EndpointHitDto endpointHitDto) {
        throw new ServerUnavailableException("Endpoint /hit method POST is unavailable");
    }

    @Override
    public List<ViewStatsDto> findByParams(String start, String end, List<String> uris, boolean unique) {
        throw new ServerUnavailableException("Endpoint /stats method GET is unavailable");
    }
}
