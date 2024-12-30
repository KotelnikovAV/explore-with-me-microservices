package ru.practicum.EndpointHit.service;

import ru.practicum.EndpointHit.model.EndpointHit;
import ru.practicum.ViewStats.model.ViewStats;

import java.util.List;

public interface EndpointHitService {
    void save(EndpointHit endpointHit);

    List<ViewStats> findByParams(String start, String end, List<String> uris, boolean unique);
}
