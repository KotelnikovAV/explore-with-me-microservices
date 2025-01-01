package ru.practicum.EndpointHit.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.EndpointHit.mapper.EndpointHitMapper;
import ru.practicum.EndpointHit.service.EndpointHitService;
import ru.practicum.ViewStats.mapper.ViewStatsMapper;
import ru.practicum.ViewStats.model.ViewStats;
import ru.practicum.client.StatClient;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class EndpointHitController implements StatClient {
    private final EndpointHitService endpointHitService;
    private final EndpointHitMapper endpointHitMapper;
    private final ViewStatsMapper viewStatsMapper;

    @SneakyThrows
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("hit")
    public void save(@Valid @RequestBody EndpointHitDto endpointHitDto) {
        log.info("Received a POST request to save statistics {}", endpointHitDto);
        endpointHitService.save(endpointHitMapper.endpointHitDtoToEndpointHit(endpointHitDto));
        Thread.sleep(500); // не понимаю, но почему-то без принудительной задержки при сохранении получение статистики
        // срабатывает через раз, как будто бы иногда данные не успевают сохраниться, что не должно быть, ведь у нас
        // не идет обработка данных в нескольких потоках, мы же отправили запрос на сохранение и ждем ответа
    }

    @GetMapping("stats")
    public List<ViewStatsDto> findByParams(@RequestParam String start,
                                           @RequestParam String end,
                                           @RequestParam(required = false) List<String> uris,
                                           @RequestParam(required = false) boolean unique) {
        log.info("Received GET request for statistics with parameters start = {}, end = {}, uris = {}, " +
                "unique = {}", start, end, uris, unique);
        List<ViewStats> viewStats = endpointHitService.findByParams(start, end, uris, unique);
        return viewStatsMapper.listViewStatsToListViewStatsDto(viewStats);
    }
}